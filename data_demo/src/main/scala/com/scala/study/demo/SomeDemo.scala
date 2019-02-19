package com.scala.study.demo

/**
  * Option类型：Some,None
  * 对应于java8中的Optional
  */
object SomeDemo {

  def main(args: Array[String]): Unit = {
    val capitals = Map("France"->"Paris", "Japan"->"Tokyo", "China"->"Beijing")
    println(capitals get "France")        //Some(Paris)
    println(capitals get "North Pole")    //None
    println(capitals get "France" get)    //抽取option类型中的值
    println((capitals get "North Pole") getOrElse "Oops")  //如果为None,设置抽取的值为Oops
    println(capitals get "France" getOrElse "Oops")    //如果不是None,则抽取的还是原来的值

    println(showCapital(Option("hahaha...")))
  }

  /**
    * 抽取option中的s变量赋值给x
    * @param x
    * @return
    */
  def showCapital(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }

}
