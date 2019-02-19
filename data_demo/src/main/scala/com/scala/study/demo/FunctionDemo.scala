package com.scala.study.demo

/**
  * function
  * 函数高阶
  */
object FunctionDemo {

  def main(args: Array[String]): Unit = {
    //函数和变量一样可以直接赋值给变量
    val hi = hiBig _
    hi("wind")
    /**
      * 函数更常使用的是匿名函数，定义的时候只需要说明输入参数的类型和函数体即可，不需要名称
      * 如果你要是用的话，一般会把这个匿名函数赋值给一个变量（其实是val常量）
      * 表现形式——（传入参数）=>{方法体}
      */
    val fun = (name:String) => {println("hello "+name)}
    fun("cloud")

    /**
      * 函数也可以作为一个参数传给函数，这极大的简化了编程的语法
      * 以前Java的方式是new一个接口实例，并且在接口实例的回调方法callback中来实现业务逻辑，
      * 这种方式是高阶函数编程方式
      * 函数作为参数的表现形式——函数名:传入参数类型*=>返回值类型
      */
    def getfun(fun:String=>Unit,name:String): Unit ={
      fun(name)
    }
    getfun(hiBig,"lalala")

    /**
      * 把数组中每个数乘以2再打印出来
      */
    Array(1 to 10:_*).map { item:Int => item*2 }.foreach { x => println(x) }

    /**
      * 函数的返回值也可以是函数
      * 下面是函数的返回值是函数的例子，这里面表明Scala实现了闭包
      * 闭包的内幕：Scala的函数背后是类和对象，所以Scala的参数都作为了对象的成员，后续可以继续访问，这就是其闭包的原理
      *
      * currying,复杂的函数式编程中经常使用，可以维护变量在内存中的状态，且实现返回函数的链式功能，可以实现非常复杂的算法和逻辑
      */
    def funcResult = (name:String)=>println(name)
    funcResult("java")
    //currying函数写法
    def funcResultTwo(message:String) = (name:String) => println(message+">>"+name)
    funcResultTwo("hello ")(" boy")
    val result = funcResultTwo("nihao ")    //这里不会执行
    result(" wind haha")

  }

  def hiBig(name:String): Unit ={
    println("hi "+name)
  }
}
