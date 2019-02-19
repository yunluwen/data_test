package com.scala.study.base

object MapTest {

  def main(args: Array[String]): Unit = {
    val scores = scala.collection.mutable.Map("spark"->16,"scala"->17,"hadoop"->18)
    scores += ("R"->223)    //map增加元素
    scores.put("",12)
    scores -= ("scala")     //减少元素
  }

}
