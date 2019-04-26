package com.zyh.batch

import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala._
/**
  * conGroup算子：
  * 将2个DataSet中的元素，按照key进行分组，一起分组2个DataSet。
  * 而groupBy值能分组一个DataSet
  */
object CoGroupFunction001scala {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val authors = env.fromElements(
      Tuple3("A001", "zhangsan", "zhangsan@qq.com"),
      Tuple3("A001", "lisi", "lisi@qq.com"),
      Tuple3("A001", "wangwu", "wangwu@qq.com"))
    val posts = env.fromElements(
      Tuple2("P001", "zhangsan"),
      Tuple2("P002", "lisi"),
      Tuple2("P003", "wangwu"),
      Tuple2("P004", "lisi"))
    // 2.scala中coGroup没有with方法来使用CoGroupFunction
    val text2 = authors.coGroup(posts).where(1).equalTo(1)

    //3.显示结果
    text2.print()
  }
}
