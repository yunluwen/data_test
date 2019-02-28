package com.zyh.house_scala.base

import scala.beans.BeanProperty

/**
  * 服务接口通用结构
  */
class ServiceResult[T]{

  @BeanProperty
  var success:Boolean = _

  @BeanProperty
  var message:String = _

  @BeanProperty
  var result:T = _

  private def this(success:Boolean) {
    this()
    this.success = success
  }

  private def this(success:Boolean,message:String) {
    this()
    this.success = success
    this.message = message
  }

  private def this(success:Boolean,message:String,result:T) {
    this()
    this.success = success
    this.message = message
    this.result = result
  }

}

//伴生对象，实现Java的静态方法
object ServiceResult {

  def success[T]():ServiceResult[T] = {
    new ServiceResult(true)
  }

  def of[T](result:T):ServiceResult[T] = {
    new ServiceResult(true,null,result)
  }

  def notFound[T]():ServiceResult[T] = {
    new ServiceResult(false,Message.NOT_FOUND.toString)
  }

  object Message extends Enumeration {
    type StatusCode = Value
    val NOT_FOUND = Value("Not Found Resource!")
    val NOT_LOGIN = Value("User not login!")
  }
}
