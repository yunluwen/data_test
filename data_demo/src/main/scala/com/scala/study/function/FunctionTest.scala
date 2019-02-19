package com.scala.study.function

/**
  * 函数
  */
object FunctionTest {   //object定义的是一个单例对象

  def main(args: Array[String]): Unit = {
    println(fun2(fun1,2))
    println(multiplier(2))
  }

  //高阶函数
  //它是将其他函数作为参数或其结果是函数的函数。
  def fun2(f: Int => String,v:Int) = fun1(v)
  def fun1[A](x:A) = s"[$x]"

  //匿名函数
  //源代码中的匿名函数称为函数文字，在运行时，函数文字被实例化为称为函数值的对象。
  var a1 = (x:Int) => s"$x"
  println(a1(6))
  var a2 = (x:Int,y:Int) => x*y
  println(a2(2,3))

  //柯里化(Currying)函数是一个带有多个参数，并引入到一个函数链中的函数，每个函数都使用一个参数。
  def strconcat(a:String)(b:String) = a+b
  println(strconcat("nihao ")("zhangyunhao"))

  // 闭包是一个函数，它返回值取决于在此函数之外声明的一个或多个变量的值。
  val factor = 3
  val multiplier = (i:Int) => i*factor


}
