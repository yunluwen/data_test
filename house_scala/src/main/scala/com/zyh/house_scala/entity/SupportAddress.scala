package com.zyh.house_scala.entity

import scala.beans.BeanProperty
import scala.language.implicitConversions    //支持scala和java的隐式类型转换

class SupportAddress {

  @BeanProperty
  var id:Long = 0L

  @BeanProperty
  var belongTo:String = ""

  @BeanProperty
  var enName:String = ""

  @BeanProperty
  var cnName:String = ""

  @BeanProperty
  var level:String = ""

  @BeanProperty
  var baiduMapLng:Double = .0

  @BeanProperty
  var baiduMapLat:Double = .0


}
