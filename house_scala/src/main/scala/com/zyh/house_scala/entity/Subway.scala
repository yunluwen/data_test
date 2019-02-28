package com.zyh.house_scala.entity

import scala.beans.BeanProperty
import scala.language.implicitConversions    //支持scala和java的隐式类型转换

class Subway {

  @BeanProperty    //查询数据库对应的bean一定要添加
  var id:Long = 0L

  @BeanProperty
  var name:String = ""

  @BeanProperty
  var cityEnName:String = ""

}
