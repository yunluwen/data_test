package com.pagerank.test

/**
  * 对象只能继承接口，才能使用接口中定义的常量
  * 不能像java那样可以通过接口名.变量名访问
  */
object TraintTest extends TestTraint {

  def main(args: Array[String]): Unit = {

    println(HELLO)
    val people = People("wind")

  }
}
