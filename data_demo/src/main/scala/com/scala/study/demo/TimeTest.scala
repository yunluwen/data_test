package com.scala.study.demo

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util._
import java.util.Calendar
import java.text.SimpleDateFormat

/**
  * java8新的日期api
  */
object TimeTest {

  def main(args: Array[String]): Unit = {
    val time = LocalTime.now()
    val current = time.format(DateTimeFormatter.ofPattern("HH:mm"))
    println(current>"11:00" && current<"11:10")

    val ca = Calendar.getInstance()
    ca.setTime(new Date())
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.format(ca.getTime)
    println(date)

  }

}
