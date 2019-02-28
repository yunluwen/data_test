package com.zyh.house_scala.service.house

import com.zyh.house_scala.base.ServiceMultiResult
import com.zyh.house_scala.base.ServiceResult
import com.zyh.house_scala.web.dto.{SubwayDTO, SubwayStationDTO, SupportAddressDTO}
import java.util

trait AddressService {

  /**
    * 获取所有支持的城市列表:city
    * @return
    */
  def findAllCities: ServiceMultiResult[SupportAddressDTO]

  /**
    * 根据英文简写获取具体区域的信息:region
    *
    * @param cityEnName
    * @param regionEnName
    * @return
    */
  def findCityAndRegion(cityEnName: String, regionEnName: String): util.Map[String,SupportAddressDTO]

  /**
    * 根据城市英文简写获取该城市所有支持的区域信息
    *
    * @param cityName
    * @return
    */
  def findAllRegionsByCityName(cityName: String): ServiceMultiResult[_]

  /**
    * 获取该城市所有的地铁线路
    *
    * @param cityEnName
    * @return
    */
  def findAllSubwayByCity(cityEnName: String): util.List[SubwayDTO]

  /**
    * 获取地铁线路所有的站点
    *
    * @param subwayId
    * @return
    */
  def findAllStationBySubway(subwayId: Long): util.List[SubwayStationDTO]

  /**
    * 获取地铁线信息
    *
    * @param subwayId
    * @return
    */
  def findSubway(subwayId: Long): ServiceResult[SubwayDTO]

  /**
    * 获取地铁站点信息
    *
    * @param stationId
    * @return
    */
  def findSubwayStation(stationId: Long): ServiceResult[SubwayStationDTO]

  /**
    * 根据城市英文简写获取城市详细信息
    *
    * @param cityEnName
    * @return
    */
  def findCity(cityEnName: String): ServiceResult[SupportAddressDTO]

  /**
    * 根据城市以及具体地位获取百度地图的经纬度
    */
  //def getBaiduMapLocation(city: String, address: String): ServiceResult[BaiduMapLocation]

  /**
    * 上传百度LBS数据
    */
  //def lbsUpload(location: BaiduMapLocation, title: String, address: String, houseId: Long, price: Int, area: Int): ServiceResult[_]

  /**
    * 移除百度LBS数据
    *
    * @param houseId
    * @return
    */
  //def removeLbs(houseId: Long): ServiceResult[_]
}
