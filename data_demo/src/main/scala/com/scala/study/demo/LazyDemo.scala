package com.scala.study.demo

/**
  * lazy懒加载，调用的时候采取初始化，懒汉式的单例模式
  * 惰性变量只能是不可变变量，并且只有在调用惰性变量时，才会去实例化这个变量。
  *
  * @author zhangyunhao
  */
object LazyDemo {

  def testLazy(): Unit ={
    println("Init ... ")
  }

  def main(args: Array[String]): Unit = {
    val obj = testLazy()    //非懒加载，执行了
    lazy val obj2 = testLazy();   //惰性变量，不执行
    obj2                    //使用惰性变量，开始执行
    println("too ... ")
  }
}
