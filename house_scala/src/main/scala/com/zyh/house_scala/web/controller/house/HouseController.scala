package com.zyh.house_scala.web.controller.house

import com.zyh.house_scala.base.{ResponseEntity, StatusCode}
import com.zyh.house_scala.service.house.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestParam,
  ResponseBody}

/**
  * 添加页面联动信息
  * @param addressService
  */
@Controller
class HouseController @Autowired() (addressService: AddressService){

  @GetMapping(Array("/address/support/cities"))
  @ResponseBody
  def getSupportCities: ResponseEntity = {
    val result = addressService.findAllCities
    if (result.getResultSize() == 0) return ResponseEntity.createByErrorCodeMessage(StatusCode.NOT_FOUND.id,StatusCode.NOT_FOUND.toString)
    println(result.result.size())
    ResponseEntity.ofSuccess(result.result)
  }

  /**
    * 获取对应城市支持区域列表
    *
    * @param cityEnName
    * @return
    */
  @GetMapping(Array("/address/support/regions"))
  @ResponseBody
  def getSupportRegions(@RequestParam(name = "city_name") cityEnName: String): ResponseEntity = {
    val addressResult = addressService.findAllRegionsByCityName(cityEnName)
    if (addressResult.getResult == null || addressResult.getTotal < 1) return ResponseEntity.
      createByErrorCodeMessage(StatusCode.NOT_FOUND.id,StatusCode.NOT_FOUND.toString)
    ResponseEntity.ofSuccess(addressResult.result)
  }

  /**
    * 获取具体城市所支持的地铁线路
    *
    * @param cityEnName
    * @return
    */
  @GetMapping(Array("/address/support/subway/line"))
  @ResponseBody
  def getSupportSubwayLine(@RequestParam(name = "city_name") cityEnName: String): ResponseEntity = {
    val subways = addressService.findAllSubwayByCity(cityEnName)
    if (subways.isEmpty) return ResponseEntity.
      createByErrorCodeMessage(StatusCode.NOT_FOUND.id,StatusCode.NOT_FOUND.toString)
    ResponseEntity.ofSuccess(subways)
  }

  /**
    * 获取对应地铁线路所支持的地铁站点
    *
    * @param subwayId
    * @return
    */
  @GetMapping(Array("/address/support/subway/station"))
  @ResponseBody
  def getSupportSubwayStation(@RequestParam(name = "subway_id") subwayId: Long): ResponseEntity = {
    val stationDTOS = addressService.findAllStationBySubway(subwayId)
    if (stationDTOS.isEmpty) return ResponseEntity.
      createByErrorCodeMessage(StatusCode.NOT_FOUND.id,StatusCode.NOT_FOUND.toString)
    ResponseEntity.ofSuccess(stationDTOS)
  }


}
