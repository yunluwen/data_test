package com.scala.study.base

object ListTest {

  def main(args: Array[String]): Unit = {
    println(List(1,2,3,4,5) partition(_ % 2 == 0))  //把List分成两部分，偶数\奇数各一部分
    println(List(1,2,3,4,5) find(_ % 2 == 0))  //找出List集合中第一个满足条件的元素，注意是第一个
    println(List(1,2,3,4,5) find(_ <= 0))
    println(List(1,2,3,4,5) takeWhile(_ < 4))  //获取符合条件的元素
    println(List(1,2,3,4,5) dropWhile(_ < 4))  //去掉符合条件的元素
    println(List(1,2,3,4,5) span(_ < 4))       //同partition，只是效率稍高


    println((1 to 100).foldLeft(0)(_ + _))  //初始值为0，从1一直加到100
    println(( 0 /: (1 to 100) ) (_ + _))

    println((1 to 5).foldRight(100) (_ - _))  //初始值为100，1-100=-99，2-（-99）=101,3-101= -98, 4-(-98)=102,5-102=-97
    println( ((1 to 5) :\ 100) (_ - _) )

    println(List(1,-3,4,2,6) sortWith(_ < _))  //从小到大排序
    println(List(1,-3,4,2,6) sortWith(_ > _))  //从大到小排序

  }

}
