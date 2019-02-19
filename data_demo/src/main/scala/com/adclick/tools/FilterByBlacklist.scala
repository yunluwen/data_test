package com.adclick.tools

import com.pagerank.dao.DaoFactory
import org.apache.spark.SparkContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.dstream.DStream

import scala.collection.mutable.ListBuffer
import com.adclick.domain.AdBlack
import org.apache.kafka.clients.consumer.ConsumerRecord

object FilterByBlacklist {

  def filterByBlacklist(sc:SparkContext,adRealTimeLogDStream:InputDStream[ConsumerRecord[String, String]]): DStream[ConsumerRecord[String, String]] ={

    adRealTimeLogDStream.transform(rdd => {
      rdd
    })

    val filteredAdRealTimeLogDStream = adRealTimeLogDStream.transform(rdd => {
      val adBlacklistDAO = DaoFactory.apply("AdClick")
      val adBlacklists:List[AdBlack] = List() //adBlacklistDAO.findAll()         //查询数据库黑名单

      val tuples = new ListBuffer[(Long,Boolean)]
      for(adBlack <- adBlacklists) {
        tuples.append((adBlack.userId,true))
      }

      //创建RDD
//      val sc = SparkContext(rdd.context)
      val blacklistRDD = sc.parallelize(tuples)

      val mappedRDD = rdd.map(consumerRecord => {
        val log = consumerRecord.value()
        val logSplited = log.split(" ")
        val userid = logSplited(3).toLong
        (userid,consumerRecord)
      })

      val joinedRDD = mappedRDD.leftOuterJoin(blacklistRDD)     //join算子优化

      val filteredRDD = joinedRDD.filter(tuple => {
        val optional = tuple._2._2    //java8里面的optional,对应于scala里面的Option,这里进行修改
        // 如果这个值存在，那么说明原始日志中的userid，join到了某个黑名单用户
        if(optional.get) {            //注意这里，存在问题,待测试  ！！！！！！！！
          false
        }
        true
      })

      val resultRDD = filteredRDD.map(filter => {
        filter._2._1
      })

      resultRDD
    })
    filteredAdRealTimeLogDStream
  }
}
