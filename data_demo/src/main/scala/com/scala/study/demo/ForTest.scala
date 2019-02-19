package com.scala.study.demo

import util.control.Breaks._
/**
  * scala替换break和continue的几种方式
  */
object ForTest {

  def main(args: Array[String]): Unit = {
//    testBreak()
    testContinue()
  }

  /**
    * break的例子
    */
  def testBreak(): Unit ={
    breakable(
      for(i<-0 until 10) {
        println(i)
        if(i==5){
          break()
        }
      }
    )

  }

  /**
    * continue的例子
    */
  def testContinue(): Unit ={
    for(i<-0 until 10){
      breakable{
        if(i==3 || i==6) {
          break()
        }
        println(i)
      }
    }
  }

}
