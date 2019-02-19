package com.scala.study.demo

/**
  * Option的模式匹配
  */
object OptionMatch {

  def main(args: Array[String]): Unit = {
    val data = Map("aaa" -> "first",
    "bbb" -> "second",
    "ccc" -> "end",
    "ddd" -> null)

    def show(value: Option[String]):String = {
      value match {
        case Some(a) => a
        case None => "No is skill"
      }
    }

    println(show(data.get("aaa")))
    println(show(data.get("ddd")))
  }
}
