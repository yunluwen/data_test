package com.zyh.house_scala.web.form

import java.util.Date

import org.springframework.format.annotation.DateTimeFormat

import scala.beans.BeanProperty
import scala.language.implicitConversions

class DataTableSearch {

  @BeanProperty
  val draw:Int = 0

  @BeanProperty
  val start:Int = 0

  @BeanProperty
  val length:Int = 0

  @BeanProperty
  val status:Int = 0

  @BeanProperty
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  val createTimeMin:Date = null

  @BeanProperty
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  val createTimeMax:Date = null

  @BeanProperty
  val city:String = null

  @BeanProperty
  val title:String = null

  @BeanProperty
  val direction:String = null

  @BeanProperty
  val orderBy:String = null
}
