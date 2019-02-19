package com.scala.study.demo

/**
  * Either/Left/Right的使用
  * 程序设计中经常会有这样的需求，一个函数（或方法）在传入不同参数时会返回不同的值。
  * 返回值是两个不相关的类型，分别为： Left 和 Right 。惯例中我们一般认为 Left 包含错误或无效值， Right包含正确或有效值。
  * 在Scala 2.10之前，Either/Right/Left类和Try/Success/Failure类是相似的效果。
  * ---------------------
  */
object EitherTest {

  def main(args: Array[String]): Unit = {
    divideBy2(1, 1) match {
      case Left(s) => println("Answer: " + s)
      case Right(i) => println("Answer: " + i)
    }
    //print "Answer: Dude, can't divide by 0"
  }

  def divideBy2(x: Int, y: Int): Either[String, Int] = {
    if(y == 0) Left("Dude, can't divide by 0")
    else Right(x / y)
  }
}
