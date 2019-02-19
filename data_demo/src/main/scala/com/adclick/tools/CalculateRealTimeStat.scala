package com.adclick.tools

import org.apache.spark.streaming.dstream.DStream
import com.session.utils.DateUtils
import java.util.Date

import com.adclick.domain.AdStat
import com.pagerank.dao.DaoFactory

import scala.collection.mutable.ListBuffer
/**
  * one
  */
object CalculateRealTimeStat {
  def calculateRealTimeStat(filteredAdRealTimeLogDStream:
                            DStream[(String,String)]): DStream[(String,Long)] ={

    val mappedDStream = filteredAdRealTimeLogDStream.map(tuple => {
      val log = tuple._2
      val logSplited = log.split(" ")
      val timestamp = logSplited(0)
      val date = new Date(timestamp.toLong)
      val datekey = DateUtils.formatDateKey(date) // yyyyMMdd
      val province = logSplited(1)
      val city = logSplited(2)
      val adid = logSplited(4)

      val key = datekey + "_" + province + "_" + city + "_" + adid
      (key,1L)
    })
    /**
      * 注意updateStateByKey算子的使用
      */
    val aggregatedDStream = mappedDStream.updateStateByKey((values: Seq[Long], state: Option[Long])=> {
      var newValue = state.getOrElse(0L)   //注意这里，和java的使用上的不同点
      for(value <- values) {
        newValue += value            //注意这里,Long类型的值相加，两个最好都是Long类型
      }
      Option(newValue)
//      第二种简化的写法
//      val currentValue = values.sum
//      Some(currentValue + state.getOrElse(0L))
    })

    aggregatedDStream.foreachRDD(rdd => {
      rdd.foreachPartition(iterator => {
        var adStats = new ListBuffer[AdStat]
        while(iterator.hasNext) {
          val tuple = iterator.next()
          val keySplited = tuple._1.split("_")
          val date = keySplited(0)
          val province = keySplited(1)
          val city = keySplited(2)
          val adid = keySplited(3).toLong

          val clickCount = tuple._2.toString.toLong
          val adStat = AdStat(date,province,city,adid,clickCount)

          adStats.append(adStat)
        }

        val adStatDAO = DaoFactory.apply("AdClick")
        adStatDAO.insert()                 //未实现
      })
    })

    aggregatedDStream
  }
}
