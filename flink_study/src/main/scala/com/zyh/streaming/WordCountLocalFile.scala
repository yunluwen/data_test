package com.zyh.streaming

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * local file or hdfs file
  * streaming
  */
object WordCountLocalFile {

  def main(args: Array[String]): Unit = {
    val senv = StreamExecutionEnvironment.getExecutionEnvironment
    val data = senv.readTextFile("/Users/zhangyunhao/test_file/test_flink/worker.txt")
    /**
      * 根据结果发现，数据是按行过来的，对数据是一行一行的处理
      */
    val count =
      data.flatMap(_.toUpperCase.split(":")).map((_,1)).keyBy(0).sum(1)
    count.print()
    senv.execute("flink streaming by local file ")

  }

}
