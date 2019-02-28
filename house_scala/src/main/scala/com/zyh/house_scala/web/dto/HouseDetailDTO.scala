package com.zyh.house_scala.web.dto

import scala.beans.BeanProperty
import scala.language.implicitConversions

class HouseDetailDTO {

  @BeanProperty
  val description:String = null

  @BeanProperty
  val layoutDesc:String = null

  @BeanProperty
  val traffic:String = null

  @BeanProperty
  val roundService:String = null

  @BeanProperty
  val rentWay:Int = 0

  @BeanProperty
  val adminId:Long = 0L

  @BeanProperty
  val address:String = null

  @BeanProperty
  val subwayLineId:Long = 0L

  @BeanProperty
  val subwayStationId:Long = 0

  @BeanProperty
  val subwayLineName:String = null

  @BeanProperty
  val subwayStationName:String = null

  override def toString = s"HousePictureDTO($description, $layoutDesc, " +
    s"$traffic, $roundService, $rentWay, $adminId, $address, $subwayLineId, " +
    s"$subwayStationId, $subwayLineName, $subwayStationName)"
}
