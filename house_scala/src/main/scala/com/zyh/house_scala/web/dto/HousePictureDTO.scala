package com.zyh.house_scala.web.dto

import com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty
import scala.language.implicitConversions

class HousePictureDTO {

  @BeanProperty
  var id:Long = 0L

  /**
    * JsonProperty
    *   此注解用于属性上，
    *   作用是把该属性的名称序列化为另外一个名称，
    *   如把trueName属性序列化为name，@JsonProperty("name")
    */
  @BeanProperty
  @JsonProperty(value = "house_id")
  var houseId:Long = 0L

  @BeanProperty
  var path:String = null

  @BeanProperty
  @JsonProperty(value = "cdn_prefix")
  var cdnPrefix:String = null

  @BeanProperty
  var width:Int = 0

  @BeanProperty
  var height:Int = 0


  override def toString = s"HousePictureDTO($id, $houseId, $path, " +
    s"$cdnPrefix, $width, $height)"
}
