package com.zyh.house_scala.web.dto

import com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

class SubwayStationDTO {

  @BeanProperty
  var id:Long = 0L

  @BeanProperty
  @JsonProperty(value = "subway_id")
  var subwayId:Long = 0L

  @BeanProperty
  var name:String = ""

}
