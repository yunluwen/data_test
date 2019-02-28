package com.zyh.house_scala.web.dto

import scala.beans.BeanProperty
import com.fasterxml.jackson.annotation.JsonProperty

class SupportAddressDTO {

  @BeanProperty          //DTO的属性也需要添加
  var id:Long = 0L

  @BeanProperty
  @JsonProperty(value = "belong_to")  //DTO的这里需要转换为和数据库一样的名字
  var belongTo:String = ""

  @BeanProperty
  @JsonProperty(value = "en_name")
  var enName:String = ""

  @BeanProperty
  @JsonProperty(value = "cn_name")
  var cnName:String = ""

  @BeanProperty
  var level:String = ""

  @BeanProperty
  var baiduMapLng:Double = .0

  @BeanProperty
  var baiduMapLat:Double = .0
}
