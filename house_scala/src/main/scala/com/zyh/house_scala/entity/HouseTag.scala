package com.zyh.house_scala.entity

import scala.beans.BeanProperty
import scala.language.implicitConversions    //支持scala和java的隐式类型转换

class HouseTag {

  @BeanProperty
  var id:Long = 0L

  @BeanProperty
  var houseId:Long = 0L

  @BeanProperty
  var name:String = ""

}
