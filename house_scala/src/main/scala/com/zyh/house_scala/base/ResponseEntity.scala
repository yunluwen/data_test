package com.zyh.house_scala.base

import com.fasterxml.jackson.annotation.JsonIgnore

import scala.beans.BeanProperty

/**
  * 接口返回的数据封装
  */
class ResponseEntity {

  @BeanProperty
  var code:Integer = 0

  @BeanProperty
  var message:String = ""

  @BeanProperty
  var data:Any = _

  /**
    * 均是不能被外部使用的
    */
  private def this(code: Integer, message: String, data: Any) {
    this()
    this.code = code
    this.message = message
    this.data = data
  }

  //带数据的构造
  private def this(data: Any) {
    this()
    this.code = 0
    this.message = "success"
    this.data = data
  }

  @JsonIgnore def isSuccess: Boolean =
    this.code.equals(StatusCode.SUCCESS.id)

}

object ResponseEntity{

  def isSuccess:Boolean = isSuccess

  def createBySuccess =
    new ResponseEntity(Integer.valueOf(StatusCode.SUCCESS.id),null,null)

  def createBySuccessMessage(message: String) =
    new ResponseEntity(Integer.valueOf(StatusCode.SUCCESS.id), message,null)

  def ofSuccess(`object`: Any) =
    new ResponseEntity(Integer.valueOf(StatusCode.SUCCESS.id), null,`object`)

  def createBySuccess(data: Any) =
    new ResponseEntity(Integer.valueOf(StatusCode.SUCCESS.id), null,data)

  def createByErrorCodeMessage(errorCode: Integer, msg: String) =
    new ResponseEntity(errorCode, msg,null)
}
