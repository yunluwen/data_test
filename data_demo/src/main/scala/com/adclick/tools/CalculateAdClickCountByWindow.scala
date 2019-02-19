package com.adclick.tools

import com.session.utils.DateUtils
import java.util.Date

import org.apache.spark.streaming.Seconds
import com.adclick.domain.AdClickTrend
import com.pagerank.dao.DaoFactory
import org.apache.kafka.clients.consumer.ConsumerRecord

import scala.collection.mutable.ListBuffer
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
/**
  * one hour window
  */
object CalculateAdClickCountByWindow {

  def calculateAdClickCountByWindow(adRealTimeLogDStream:InputDStream[ConsumerRecord[String, String]]): Unit ={
    val pairDStream = adRealTimeLogDStream.map(consumerRecord => {
      val logSplited = consumerRecord.value().split(" ")
      val timeMinute = DateUtils.formatTimeMinute(
        new Date(logSplited(0).toLong))
      val adid = logSplited(4).toLong

      (timeMinute+"_"+adid,1L)
    })

    //注意reduceByKeyAndWindow在scala和java中使用的不同点
    //Seconds(60)表示窗口的宽度   Seconds(10)表示多久滑动一次(滑动的时间长度)
    val aggrRDD = pairDStream.reduceByKeyAndWindow((v1:Long,v2:Long) => v1+v2,Seconds(60), Seconds(10))

    aggrRDD.foreachRDD(rdd => {
      rdd.foreachPartition(iterator => {
        var adClickTrends = new ListBuffer[AdClickTrend]
        while(iterator.hasNext){
          val tuple = iterator.next()
          val keySplited = tuple._1.split("_")
          val dateMinute = keySplited(0)
          val adid = keySplited(1).toLong
          val clickCount = tuple._2

          val date = DateUtils.formatDate(DateUtils.parseDateKey(
            dateMinute.substring(0, 8)))
          val hour = dateMinute.substring(8, 10)
          val minute = dateMinute.substring(10)

          val adClickTrend = AdClickTrend(date,hour,minute,adid,clickCount)
          adClickTrends.append(adClickTrend)
        }

        val adClickDao = DaoFactory.apply("AdClick")
        adClickDao.insert()                //未实现

      })
    })
  }

}
