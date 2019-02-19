package com.spark.study.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * updateStateByKey
  */
object UpdateStateByKeyDemo {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("UpdateStateByKeyDemo").setMaster("local")
    val ssc = new StreamingContext(conf,Seconds(20))
    //要使用updateStateByKey方法，必须设置Checkpoint。
    ssc.checkpoint("/Users/zhangyunhao/test_file/")
    val socketLines = ssc.socketTextStream("10.1.25.14",9999)

    socketLines.flatMap(_.split(" ")).map(word=>(word,1)).updateStateByKey(
      (currValues:Seq[Int],preValue:Option[Int]) =>{
      //将目前值相加
      var currValueSum = 0
      for(currValue <- currValues){
        currValueSum += currValue
      }
      //上面其实可以这样：val currValueSum = currValues.sum
      //上面的Int类型都可以用对象类型替换
      Some(currValueSum + preValue.getOrElse(0)) //目前值的和加上历史值
    }).foreachRDD(rdd => {
      rdd.foreachPartition(iterator => {
        while(iterator.hasNext) {
          println(iterator.next())
        }
      })
    })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }
}
