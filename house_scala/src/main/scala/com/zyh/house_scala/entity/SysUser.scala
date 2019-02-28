package com.zyh.house_scala.entity

import java.util.Date
import scala.beans.BeanProperty
import scala.language.implicitConversions

/**
  * 系统用户
  */
class SysUser {

  @BeanProperty
  val id:Long = 0L

  @BeanProperty
  val name:String = null

  @BeanProperty
  val password:String = null

  @BeanProperty
  val email:String = null

  @BeanProperty
  val phoneNumber:String = null

  @BeanProperty
  val status:Int = 0

  @BeanProperty
  val createTime:Date = null

  @BeanProperty
  val lastLoginTime:Date = null

  @BeanProperty
  val lastUpdateTime:Date = null

  @BeanProperty
  val avatar:String = null

}
