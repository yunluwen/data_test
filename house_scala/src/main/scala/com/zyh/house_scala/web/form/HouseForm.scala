package com.zyh.house_scala.web.form

import javax.validation.constraints.{Max, Min, NotNull, Size}

class HouseForm {

  var id:Long = 0L

  @NotNull(message = "大标题不允许为空!")
  @Size(min = 1, max = 30, message = "标题长度必须在1~30之间")
  var title:String = ""

  @NotNull(message = "必须选中一个城市")
  @Size(min = 1, message = "非法的城市")
  var cityEnName:String = ""

  @NotNull(message = "必须选中一个地区")
  @Size(min = 1, message = "非法的地区")
  var regionEnName:String = ""

  @NotNull(message = "必须填写街道")
  @Size(min = 1, message = "非法的街道")
  var street = null

  @NotNull(message = "必须填写小区")
  var district = null

  @NotNull(message = "详细地址不允许为空!")
  @Size(min = 1, max = 30, message = "详细地址长度必须在1~30之间")
  var detailAddress = null

  @NotNull(message = "必须填写卧室数量")
  @Min(value = 1, message = "非法的卧室数量")
  var room = null

  var parlour = 0

  @NotNull(message = "必须填写所属楼层")
  var floor = null

  @NotNull(message = "必须填写总楼层")
  var totalFloor = null

  @NotNull(message = "必须填写房屋朝向")
  var direction = null

  @NotNull(message = "必须填写建筑起始时间")
  @Min(value = 1900, message = "非法的建筑起始时间")
  var buildYear = null

  @NotNull(message = "必须填写面积")
  @Min(value = 1)
  var area = null

  @NotNull(message = "必须填写租赁价格")
  @Min(value = 1)
  var price = null

  @NotNull(message = "必须选中一个租赁方式")
  @Min(value = 0)
  @Max(value = 1)
  var rentWay:Int = 0

  var subwayLineId:Long = 0L

  var subwayStationId:Long = 0L

  var distanceToSubway:Int = -1

  var layoutDesc:String = ""

  var roundService:String = ""

  var traffic:String = ""

  @Size(max = 255)
  var description:String = ""

  var cover:String = ""

  var tags:String = ""

  var photos:String = ""


  override def toString = s"HouseForm(id=$id, title=$title, " +
    s"cityEnName=$cityEnName, regionEnName=$regionEnName, " +
    s"street=$street, district=$district, detailAddress=$detailAddress, " +
    s"room=$room, parlour=$parlour, floor=$floor, totalFloor=$totalFloor, " +
    s"direction=$direction, buildYear=$buildYear, area=$area, price=$price, " +
    s"rentWay=$rentWay, subwayLineId=$subwayLineId, subwayStationId=$subwayStationId, " +
    s"distanceToSubway=$distanceToSubway, layoutDesc=$layoutDesc, " +
    s"roundService=$roundService, traffic=$traffic, description=$description, " +
    s"cover=$cover, tags=$tags, photos=$photos)"
}
