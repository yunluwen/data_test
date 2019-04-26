package com.zyh.streaming.countwindow.test

import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
/**
  * 自定义DataSource
  */
class StreamDataSource extends RichParallelSourceFunction[Tuple2[String, String]]{

  var running = true

  override def run(sourceContext: SourceContext[Tuple2[String, String]]): Unit = {
    //scala中数组的使用
    val elements:Array[Tuple2[String,String]] = Array(
      ("a", "1"),
      ("a", "2"),
      ("a", "3"),
      ("a", "4"),
      ("a", "5"),
      ("a", "6"),
      ("b", "7"),
      ("b", "8"),
      ("b", "9"),
      ("b", "0")
    )
    var count = 0
    while(running && count<elements.length){
      sourceContext.collect(new Tuple2[String,String](
        elements(count)._1,elements(count)._2))
      count += 1
      Thread.sleep(1000)    //休眠1s
    }
  }

  override def cancel(): Unit = {
    running = false
  }
}
