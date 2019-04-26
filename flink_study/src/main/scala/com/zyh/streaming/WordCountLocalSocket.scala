package com.zyh.streaming

//0.引用必要的元素
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
/**
  * streaming by socket
  *
  * 1.发送数据命令
  * nc -lk 9999
  * 2.发送数据内容
  * good good study
  * day day up
  */
object WordCountLocalSocket {

  def main(args: Array[String]): Unit = {
    //1、创建流式处理环境
    val senv = StreamExecutionEnvironment.getExecutionEnvironment

    //2、准备数据
    val data = senv.socketTextStream("localhost",9999)
    //3、进行wc计算
    val counts = data.flatMap(_.toUpperCase.split(" ")).filter(_.nonEmpty)
      .map((_,1))
      .keyBy(0)                        //根据第一个值进行分组
      .timeWindow(Time.seconds(5))   //注意这个算子的作用,计算单位时间内的数据
      .sum(1)                        //累加第二个值

    counts.print()

    senv.execute("flink streaming by ")
  }
}
