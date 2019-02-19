package com.pagerank.main

import com.alibaba.fastjson.{JSON, JSONObject}
import com.session.constants.Constants
import com.session.dao.TaskDaoFactory
import com.session.init.Init
import com.session.utils.{NumberUtils, ParamUtils}
import com.pagerank.utils.{SQLUtils, SparkUtils}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.storage.StorageLevel
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import util.control.Breaks._

/**
  * page rank 1.6.0
  * page rank 2.1.0
  * scala list : unmutable,update will get new list
  * listBuffer : mutable
  * @author zhangyunhao
  */
object PageOneStepConvertRateSpark {

  def main(args: Array[String]): Unit = {

    val sc = Init.initSparkContext()
    val sqlContext = SQLUtils.getRDDData(sc._1,"user_visit_action.txt")

    val taskDao =  TaskDaoFactory.getTaskDao()
    val taskId = ParamUtils.getTaskIdFromArgs(args(0),Constants.SPARK_LOCAL_SESSION_TASKID).longValue()
    val task = if (taskId > 0) taskDao.getTask(taskId) else null
    if (task == null) println("Can't find task by id: " + taskId)

    val taskParam = JSON.parseObject(task.getTaskParam())

    val actionRDD = SparkUtils.getActionRDDByDateRange(sqlContext,taskParam)

    var sessionid2actionRDD = getSessionid2actionRDD(actionRDD)

    sessionid2actionRDD = sessionid2actionRDD.persist(StorageLevel.MEMORY_ONLY)  //cache()

    val sessionid2actionsRDD = sessionid2actionRDD.groupByKey()
    val pageSplitRDD = generateAndMatchPageSplit(sc._1,sessionid2actionsRDD,taskParam)
    val pageSplitPvMap = pageSplitRDD.countByKey()

    val startPagePv:Long = getStartPagePv(taskParam,sessionid2actionsRDD)

    val convertRateMap:Map[String,Double] =
      computePageSplitConvertRate(taskParam,pageSplitPvMap,startPagePv)
    persistConvertRate(taskId,convertRateMap)
  }

  /**
    * get data
    * @param actionRDD
    * @return
    */
  def getSessionid2actionRDD(actionRDD: RDD[Row]): RDD[(String,Row)] ={
    actionRDD.map(action => (action.getString(2),action))
  }

  /**
    * cut data
    * @param sc
    * @param sessionid2actionsRDD
    * @param taskParam
    */
  def generateAndMatchPageSplit(sc: SparkContext,sessionid2actionsRDD: RDD[(String,Iterable[Row])],
                                taskParam: JSONObject): RDD[(String,Int)] = {

    val targetPageFlow = ParamUtils.getParam(taskParam, Constants.PARAM_TARGET_PAGE_FLOW)
    //broadcast
    val targetPageFlowBroadcast = sc.broadcast(targetPageFlow)
    return sessionid2actionsRDD.flatMap( flat =>  {
      val list: ListBuffer[(String, Int)] = ListBuffer()
      val iterator = flat._2.iterator
      val targetPages = targetPageFlowBroadcast.value.split(",")
      val rows: ListBuffer[Row] = ListBuffer()
      while(iterator.hasNext) {
        rows.append(iterator.next())
      }

      val sortList = rows.sortBy(f => {
        f.getString(4)
      })(Ordering.String.reverse)

      var lastPageId:Long = 0L
      for(row <- sortList) {
        val pageId:Long = row.getLong(3)     //Integer Type
        breakable {
          if (lastPageId == 0L) {
            lastPageId = pageId              //continue
            break()
          }
        }

        val pageSplit:String = lastPageId + "_" + pageId
        breakable(
          for(i <- 1 to targetPages.length) {
            val targetPageSplit = targetPages(i-1) + "_" + targetPages(i)
            if(pageSplit.equals(targetPageSplit)) {
              list.append((pageSplit,1))
              break()                        //break
            }
          }
        )

        lastPageId = pageId
      }
      list.toList
    })

  }

  /**
    * first pv
    * @param taskParam
    * @param sessionid2actionsRDD
    * @return
    */
  def getStartPagePv(taskParam: JSONObject,sessionid2actionsRDD: RDD[(String,Iterable[Row])]): Long ={
    val targetPageFlow = ParamUtils.getParam(taskParam, Constants.PARAM_TARGET_PAGE_FLOW)
    val startPageId = targetPageFlow.split(",")(0).toLong
    val startPageRDD = sessionid2actionsRDD.flatMap( flat => {
      //listBuffer的使用
      val list: ListBuffer[Long] = ListBuffer()
      val pageList: List[Long] = List()
      val iterator = flat._2.iterator
      while(iterator.hasNext) {
        val row: Row = iterator.next()
        val currentPageId: Long = row.getLong(3)   //Integer Type
        if(currentPageId == startPageId) {
          list.append(currentPageId)
          pageList:+currentPageId
        }
      }
      list.toList
    })

    return startPageRDD.count()

  }

  /**
    * computer
    * @param taskParam
    * @param pageSplitPvMap
    * @param startPagePv
    * @return
    */
  def computePageSplitConvertRate(taskParam: JSONObject,
                                  pageSplitPvMap: scala.collection.Map[String,Long],
                                  startPagePv:Long): Map[String,Double] ={

    var convertRateMap: Map[String,Double] = Map()          // mutable Map

    val targetPages = ParamUtils.getParam(taskParam,Constants.PARAM_TARGET_PAGE_FLOW).split(",")
    var lastPageSplitPv = 0L
    for(i <- 1 to targetPages.length) {
      val targetPageSplit = targetPages(i - 1) + "_" + targetPages(i)
      val targetPageSplitPv: Long = pageSplitPvMap.get(targetPageSplit).toString.toLong
      var convertRate: Double = 0.0

      if(i==1) {
        convertRate = NumberUtils.formatDouble(targetPageSplitPv.toDouble
          / startPagePv.toDouble, 2)
      } else{
        convertRate = NumberUtils.formatDouble(targetPageSplitPv.toDouble
          / lastPageSplitPv.toDouble, 2)
      }
      /** 模式匹配
      i match {
        case 1 => {
          convertRate = NumberUtils.formatDouble(targetPageSplitPv.toDouble
            / startPagePv.toDouble, 2)
        }
        case _ => {
          convertRate = NumberUtils.formatDouble(targetPageSplitPv.toDouble
            / lastPageSplitPv.toDouble, 2)
        }
      }
        */
      convertRateMap.put(targetPageSplit,convertRate)

      lastPageSplitPv = targetPageSplitPv
    }

    return convertRateMap
  }

  /**
    * db
    * @param taskid
    * @param convertRateMap
    */
  def persistConvertRate(taskid:Long,convertRateMap:Map[String,Double]): Unit ={

    val builder = new StringBuilder
    convertRateMap.keys.foreach(i => {
      val pageSplit: String = i
      val convertRate: Double = convertRateMap(i)
      builder.append(pageSplit + "=" + convertRate + "|")
    })

    var convertRate = builder.toString()
    convertRate = convertRate.substring(0,convertRate.length-1)

    //insert db

  }

}
