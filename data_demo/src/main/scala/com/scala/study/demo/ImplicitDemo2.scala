package com.scala.study.demo

/**
  * 注意事项：
  * 1） 隐式转换函数的函数名可以是任意的，与函数名称无关，只与函数签名（函数参数和返回值类型）有关。
  *
  * 2）如果当前作用域中存在函数签名（函数参数和返回值类型）相同但函数名称不同的两个隐式转换函数，则在进行隐式转换时会报错。
  */
object ImplicitDemo2 {

  def main(args: Array[String]): Unit = {
    /**
      * 隐式转换函数
      * @param f
      * @return
      */
    //将float转换为int的隐式转换函数,与数据的类型无关，与数据有关
    implicit def float2int(f: Float)= f.toInt
    val a:Int = 2.8f
    println(a)

    /**
      * 隐式类，只与类的参数类型和返回值类型有关
      * @param name
      */
    implicit class Dog(name:String){
      def getvoice(message:String){
        println(name+"\t"+message)
      }
    }

    "bob".getvoice("hahaha...")

    /**
      * 如果当前作用域中存在函数签名（函数参数和返回值类型）相同的隐式函数和隐式类，两者互不影响，不会出现问题
      * @param name
      */
    implicit class Dog2(name:Float){
      def float2int(message:String){
        println(name+"\t"+message)
      }
    }

    "bob".getvoice("hahaha...")
    2.0f.float2int("mmmmm...")

    /**
      * 注意事项： 隐式类的主构造函数参数有且仅有一个！之所以只能有一个参数，
      * 是因为隐式转换是将一种类型转换为另外一种类型，源类型与目标类型是一一对应的
      */
//    implicit class Dog3(name:String,age:Int){    //错误的
//      def getvoice(message:String){
//        println(name+"\t"+message)
//      }
//    }

    /**
      * 隐式参数和隐式值的使用
      */
    implicit val age :Int = 23
    def getTpp(name:String)(implicit age:Int): Unit ={
      println(name+"\t"+age)
    }

    getTpp("wind")

  }

}


