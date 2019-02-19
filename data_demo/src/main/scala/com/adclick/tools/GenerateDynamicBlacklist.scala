package com.adclick.tools

import org.apache.spark.streaming.dstream.DStream
import java.util.Date

import com.session.utils.DateUtils
import com.adclick.domain.{ AdUserClickCount, AdBlack }
import com.pagerank.dao.DaoFactory

import scala.collection.mutable.ListBuffer

/**
  * dynamic
  */
object GenerateDynamicBlacklist {

  def generateDynamicBlacklist(filteredAdRealTimeLogDStream:DStream[(String,String)]): Unit ={

    val dailyUserAdClickDStream = filteredAdRealTimeLogDStream.map(tuple => {
      val log = tuple._2
      val logSplited = log.split(" ")
      val timestamp = logSplited(0)
      val date = new Date(timestamp.toLong)
      val datekey = DateUtils.formatDateKey(date)    //scala版本的日期处理工具类

      val userId = logSplited(3).toLong
      val adId = logSplited(4).toLong

      val key = datekey + "_" + userId + "_" + adId
      (key,1L)
    })

    val dailyUserAdClickCountDStream = dailyUserAdClickDStream.reduceByKey(_+_)
    dailyUserAdClickCountDStream.foreachRDD(rdd => {
      rdd.foreachPartition(iterator => {
        var adUserClickCounts = new ListBuffer[AdUserClickCount]
        while(iterator.hasNext) {
          val tuple = iterator.next()
          val keySplited = tuple._1.split("_")
          val date = DateUtils.formatDate(DateUtils.parseDateKey(keySplited(0)))
          val userid = keySplited(1).toLong
          val adid = keySplited(2).toLong

          val clickCount = tuple._2
          val adUserClickCount = AdUserClickCount(date,userid,adid,clickCount)
          adUserClickCounts.append(adUserClickCount)
        }
        //click count add DB
        val adBlacklistDAO = DaoFactory.apply("AdClick")
        adBlacklistDAO.insert()          //未实现，这里使用反射读取xml配置文件，添加操作类以及sql

      })
    })

    val blacklistDStream = dailyUserAdClickCountDStream.filter(tuple => {
      val key = tuple._1           //data_userid_adid
      val keySplited = key.split("_")

      val date = DateUtils.formatDate(DateUtils.parseDateKey(keySplited(0)))
      val userid = keySplited(1).toLong
      val adid = keySplited(2).toLong
      val adUserClickCountDAO = DaoFactory.apply("AdClick")
      val clickCount = adUserClickCountDAO.findClickCountByMultiKey(
        date, userid, adid);

      if(clickCount >= 100){
        true
      }
      false
    })

    val blacklistUseridDStream = blacklistDStream.map(tuple => {
      val key = tuple._1           //data_userid_adid
      val keySplited = key.split("_")
      val userid = keySplited(1).toLong
      userid
    })

    val distinctBlacklistUseridDStream = blacklistUseridDStream.transform(rdd => {
      rdd.distinct()    //去重
    })

    distinctBlacklistUseridDStream.foreachRDD(rdd => {
      rdd.foreachPartition(iterator => {
        var adBlacklists = new ListBuffer[AdBlack]     //测试listBUffer设置成val的时候

        while(iterator.hasNext) {
          val userid = iterator.next()
          val adBlack = AdBlack(userid)
          adBlacklists.append(adBlack)
        }
        val adBlacklistDAO = DaoFactory.apply("AdClick")
        adBlacklistDAO.insert()                       //添加sql，未实现
      })

    })

  }
}
