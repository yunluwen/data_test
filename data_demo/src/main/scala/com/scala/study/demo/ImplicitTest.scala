package com.scala.study.demo

/**
  * 隐式转换实现类似模式匹配的功能
  * 隐式对象
  * @author zhangyunhao
  */
object ImplicitTest extends App{
  //定义一个trait Multiplicable
  trait Multiplicable[T]{
    def multiply(x: T):T
  }

  //定义一个隐式对象，用于整型数据相乘
  implicit object MultiplicableInt extends Multiplicable[Int]{
    def multiply(x: Int): Int = x*x
  }

  //定义一个用于字符串相乘的隐式对象
  implicit object MultiplicableString extends Multiplicable[String]{
    def multiply(x: String): String = x*2
  }

  //定义一个函数，函数具有泛型参数
  def multiply[T: Multiplicable](x: T): T ={
    //implicitly方法，访问隐式对象
    val ev = implicitly[Multiplicable[T]]
    //根据具体的类型调用相应的隐式对象中的方法
    ev.multiply(x)
  }

  //调用隐式对象 MultiplicableInt 中的方法
  println(multiply(5))                       //25

  //调用隐式对象 MultiplicableString 中的方法
  println(multiply("5"))                     //55

}


