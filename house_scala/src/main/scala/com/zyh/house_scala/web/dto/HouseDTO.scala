package com.zyh.house_scala.web.dto

import java.util.Date
import java.util.{List => JavaList}

import scala.collection.immutable.{List => _}
import scala.beans.BeanProperty
import scala.language.implicitConversions

class HouseDTO extends Serializable{

  @BeanProperty
  val id:Long = 0L

  @BeanProperty
  val title:String = null

  @BeanProperty
  val price:Int = 0

  @BeanProperty
  val area:Int = 0

  @BeanProperty
  val direction:Int = 0

  @BeanProperty
  val room:Int = 0

  @BeanProperty
  val parlour:Int = 0

  @BeanProperty
  val bathroom:Int = 0

  @BeanProperty
  val floor:Int = 0

  @BeanProperty
  val adminId:Long = 0

  @BeanProperty
  val district:String = null

  @BeanProperty
  val totalFloor:Int = 0

  @BeanProperty
  val watchTimes:Int = 0

  @BeanProperty
  val buildYear:Int = 0

  @BeanProperty
  val status:Int = 0

  @BeanProperty
  val createTime:Date = null

  @BeanProperty
  val lastUpdateTime:Date = null

  @BeanProperty
  val cityEnName:String = null

  @BeanProperty
  val regionEnName:String = null

  @BeanProperty
  val street:String = null

  @BeanProperty
  val cover:String = null

  @BeanProperty
  val distanceToSubway:Int = 0

  @BeanProperty
  var houseDetail:HouseDetailDTO = null

  @BeanProperty
  var tags:JavaList[String] = null

  @BeanProperty
  var pictures:JavaList[HousePictureDTO] = null

  @BeanProperty
  var subscribeStatus:Int = 0


  override def toString = s"HouseDTO(" +
    s"$id, $title, $price, $area, $direction, " +
    s"$room, $parlour, $bathroom, $floor, $adminId," +
    s" $district, $totalFloor, $watchTimes, $buildYear, " +
    s"$status, $createTime, $lastUpdateTime, $cityEnName, " +
    s"$regionEnName, $street, $cover, $distanceToSubway, " +
    s"$houseDetail, $tags, $pictures, $subscribeStatus)"
}
