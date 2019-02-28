package com.zyh.house_scala.entity

import scala.beans.BeanProperty
import scala.language.implicitConversions    //支持scala和java的隐式类型转换

/**
  * 验证规则
  * 用户角色
  */
class Role {

  @BeanProperty
  val id:Long = 0L
  @BeanProperty
  val userId:Long = 0L
  @BeanProperty
  val name:String = ""

}
