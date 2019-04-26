package com.zyh.batch

import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala._
/**
  * join算子：
  * join将两个DataSet按照一定的关联度进行类似SQL中的Join操作。
  */
object JoinFunction001scala {

  def main(args: Array[String]): Unit = {
    // 1.设置运行环境,并创造测试数据
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
    // 2.scala中没有with方法来使用JoinFunction,可以执行JoinFunction返回自己想要的数据类型
    val result = authors.join(posts).where(1).equalTo(1)

    result.print()
  }
}
