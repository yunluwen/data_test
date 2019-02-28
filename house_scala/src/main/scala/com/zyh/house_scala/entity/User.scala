package com.zyh.house_scala.entity

import scala.beans.BeanProperty
import scala.language.implicitConversions    //支持scala和java的隐式类型转换

// Simple Group POJO
class User{

  /**
    * BeanProperty 生成与Java兼容的set/get方法，但属性不能为private[house_scala]，设置为包级，则其他包无法访问
    * implicitConversions 支持Scala-Java隐式类型转换
    */
  //如果不使用BeanProperty注解，则可以加private
  //a.name -- 调用 a.name方法
  //a.name = "foo" 调用a.name_ (x:String) = xxx = x

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var name:String = _

  override def toString: String = {
    s"id:${this.id} \t name:${this.name}"
  }

}