package com.zyh.house_scala.base

import scala.language.implicitConversions
import java.util.{List => JavaList}

import scala.beans.BeanProperty
import scala.collection.immutable.{List => _}

/**
  * 通用多结果service返回结构
  */
class ServiceMultiResult[T] {

  @BeanProperty
  var total:Long = _

  @BeanProperty
  var result:JavaList[T] = _

  private def this(total:Long,result:JavaList[T]){
    this()
    this.total = total
    this.result = result
  }

  def getResultSize():Int = {
    if(this.result == null){
      return 0
    }
    this.result.size()
  }

}

//伴生对象，实现Java的静态方法
object ServiceMultiResult {

  def of[T](total:Long,result:JavaList[T]):ServiceMultiResult[T] = {
    new ServiceMultiResult(total,result)
  }

}
