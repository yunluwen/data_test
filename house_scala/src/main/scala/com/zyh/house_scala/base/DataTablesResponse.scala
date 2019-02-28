package com.zyh.house_scala.base

import scala.beans.BeanProperty
import scala.language.implicitConversions

/**
  * scala继承的特点和重写的特点
  * 子类的辅助构造器最终都会调用主构造器，
  * 只有主构造器可以调用超类的构造器。
  * 辅助构造器永远都不可能直接调用超类的构造器。
  * 在Scala的构造器中，你不能调用super(params)，不像Java，可以用这种方式调用超类构造器。
  *
  * @param code
  * @param message
  * @param data
  */
class DataTablesResponse(code: Integer,message:String,data:Any)
  extends ResponseEntity {

  //验证结果
  @BeanProperty
  var draw:Int = 0

  @BeanProperty
  var recordsTotal:Long = 0L

  @BeanProperty
  var recordsFiltered:Long = 0L

  def this(code: Integer,data:Any) {
    this(code,null,data)
  }

}




