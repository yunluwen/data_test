package com.zyh.house_scala.web.dto

import com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

class SubwayDTO {

  @BeanProperty
  var id:Long = 0L

  @BeanProperty
  var name:String = ""

  @BeanProperty
  @JsonProperty(value = "city_en_name")
  var cityEnName:String = ""

}
