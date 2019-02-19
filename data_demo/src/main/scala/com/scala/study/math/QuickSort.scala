package com.scala.study.math

object QuickSort {

  def sort(ls: List[Int]): List[Int] = {
    ls match {
      case Nil => Nil  //列表是空列表，返回空列表
      case base :: tail => {  //不是空列表,::可以用于模式匹配
        println(base)
        //将列表尾部分成比首部元素小的部分和比首部元素大的部分
        val (left, right) = tail.partition(_ < base)
        //组合成一个新的列表——sort(比首部小的部分)+首部+sort(比首部大的部分)
        sort(left) ::: base :: sort(right)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val list = List(2,4,7,9,0,2,3,4,6)
    println(sort(list))
  }

}
