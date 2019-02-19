package com.scala.study.demo

import scala.util.{Try, Success, Failure}
import scala.io.Source
/**
  * Try/Success/Failue的使用:
  * 对于有可能抛出异常的操作,我们可以使用Try来包裹它，
  * 得到Try的子类Success或者Failure，
  * 如果计算成功，返回Success的实例，
  * 如果抛出异常，返回Failure并携带相关信息。
  *
  * ---------------------
  * Try有类似集合的操作 filter, flatMap, flatten, foreach, map
  * get, getOrElse, orElse方法
  * toOption可以转化为Option
  * recover，recoverWith，transform可以让你优雅地处理Success和Failure的结果
  * ---------------------
  */
object TryTest {
  def main(args: Array[String]): Unit = {
//    println(divideBy(1, 1))            //Success
//    println(divideBy(1, 0))            //Failure(java.lang.ArithmeticException: / by zero)
//    println(divideBy(1, 1).getOrElse(0)) // 1
//    println(divideBy(1, 0).getOrElse(0)) //0
//    divideBy(1, 1).foreach(println) // 1
//    divideBy(1, 0).foreach(println) // no print
//
//    divideBy(1, 1) match {
//      case Success(s) => println(s"Success, value is: $s")
//      case Failure(s) => println(s"Failed, message is: $s")
//    }
//
//    val fileName = "/Users/zhangyunhao/test_file/sc.txt"
//    readTextFile(fileName) match {
//      case Success(lines) => lines.foreach(println)
//      case Failure(message) => println(message)
//    }

    testTry()
  }

  def divideBy(x: Int, y: Int): Try[Int] = {
    Try(x / y)
  }

  /**
    * 读取文件
    */
  def readTextFile(fileName: String): Try[List[String]] ={
    Try(Source.fromFile(fileName).getLines().toList)   //获取一个文件的全部信息
  }

  def testTry(): Unit ={
    val str = "info:qingshan"
    val info = str.split(":")
    val result = Try(info(1)) match {
      case Success(s) => s
      case Failure(f) => f
    }
    println(result)
  }

}
