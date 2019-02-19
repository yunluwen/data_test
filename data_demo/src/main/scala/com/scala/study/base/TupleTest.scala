package com.scala.study.base

/**
  * Tuple测试
  */
object TupleTest {

  def main(args: Array[String]): Unit = {
    val tuple = (1,3,2.14,"spark","scala")

    /**
      * partition方法：
      * 把大写的组成一组，其余的组成一组，注意空格也属于字符
      */
    "Spark Hadoop".partition(_.isUpper)
  }

}
