package com.scala.study.base

/**
  * 返回结果状态码枚举
  */
object StatusCode extends Enumeration{

  type StatusCode = Value   //声明枚举对外暴露的变量类型
  val SUCCESS = Value(200, "OK")
  val BAD_REQUEST = Value(400, "Bad request")
  val INTERNAL_SERVER_ERROR = Value(500, "UnKnown Internal Error")
  val NOT_VALID_PARAM = Value(-1, "Not Valid Params")
  val NOT_SUPPORTED_OPERATION = Value(-2, "Operation not Supported")
  val NOT_LOGIN = Value(403, "Not Login")
  val NOT_FOUND = Value(404, "Not Found")

}
