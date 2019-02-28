package com.zyh.house_scala.service.house.impl

import com.zyh.house_scala.base.{ServiceMultiResult, ServiceResult}
import org.springframework.stereotype.Service
import com.zyh.house_scala.service.house.AddressService
import com.zyh.house_scala.web.dto.{SubwayDTO, SubwayStationDTO, SupportAddressDTO}
import java.util

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.zyh.house_scala.repository.{SupportAddressDao,SubwayDao,SubwayStationDao}
import org.springframework.beans.factory.annotation.Autowired
import com.zyh.house_scala.entity.{SupportAddress,Subway,SubwayStation}
import org.modelmapper.ModelMapper

/**
  * 添加java和scala集合类库隐式转换，可以使java和scala的集合类库互操作
  */
import scala.collection.JavaConversions._

@Service
class AddressServiceImpl @Autowired() (supportAddressDao: SupportAddressDao,
                                       modelMapper: ModelMapper,subwayDao: SubwayDao
                                      ,subwayStationDao: SubwayStationDao) extends AddressService {

  override def findAllCities: ServiceMultiResult[SupportAddressDTO] = {
    val address:util.List[SupportAddress] = supportAddressDao.
      selectList(new QueryWrapper[SupportAddress]().eq("level","city"))

    val addressDTOs:util.List[SupportAddressDTO] = new util.ArrayList[SupportAddressDTO]()
    for(supportAddress <- address){
      //将supportAddress转换为SupportAddressDTO
      val target:SupportAddressDTO =
        modelMapper.map(supportAddress,classOf[SupportAddressDTO])
      addressDTOs.add(target)
    }
    ServiceMultiResult.of[SupportAddressDTO](addressDTOs.size(), addressDTOs)
  }

  override def findCityAndRegion(cityEnName: String, regionEnName: String):
  util.Map[String, SupportAddressDTO] = {
    val result:util.Map[String, SupportAddressDTO] =
      new util.HashMap[String, SupportAddressDTO]()
    val city:SupportAddress = supportAddressDao.
      selectOne(new QueryWrapper[SupportAddress]().
        eq("level","city").eq("en_name",cityEnName))

    val region:SupportAddress = supportAddressDao.
      selectOne(new QueryWrapper[SupportAddress]().
      eq("en_name",regionEnName).eq("belong_to",city.enName))

    result.put("city",modelMapper.map(city,classOf[SupportAddressDTO]))
    result.put("region",modelMapper.map(region,classOf[SupportAddressDTO]))
    result
  }

  override def findAllRegionsByCityName(cityName: String): ServiceMultiResult[SupportAddressDTO] = {
    if(cityName == null){
      return ServiceMultiResult.of[SupportAddressDTO](0,null)
    }
    val result:util.List[SupportAddressDTO] = new util.ArrayList[SupportAddressDTO]()
    val city:SupportAddress = supportAddressDao.
      selectOne(new QueryWrapper[SupportAddress]().
        eq("level","city").
        eq("cn_name",cityName))

    val regions:util.List[SupportAddress] = supportAddressDao.
      selectList(new QueryWrapper[SupportAddress]().
        eq("level","region").eq("belong_to",city.belongTo))
    for(region <- regions){
      result.add(modelMapper.map(region,classOf[SupportAddressDTO]))
    }
    ServiceMultiResult.of[SupportAddressDTO](regions.size(),result)
  }

  override def findAllSubwayByCity(cityEnName: String): util.List[SubwayDTO] = {
    val result:util.List[SubwayDTO] = new util.ArrayList[SubwayDTO]()
    val subways:util.List[Subway] = subwayDao.selectList(new QueryWrapper[Subway]().
      eq("city_en_name",cityEnName))
    if(subways.isEmpty){
      return result
    }
    for(subway <- subways){
      result.add(modelMapper.map(subway,classOf[SubwayDTO]))
    }
    result
  }

  override def findAllStationBySubway(subwayId: Long): util.List[SubwayStationDTO] = {
    val result:util.List[SubwayStationDTO] = new util.ArrayList[SubwayStationDTO]()
    val stations:util.List[SubwayStation] = subwayStationDao.
      selectList(new QueryWrapper[SubwayStation]().
      eq("subway_id",subwayId))
    if(stations.isEmpty){
      return result
    }
    for(station <- stations){
      result.add(modelMapper.map(station,classOf[SubwayStationDTO]))
    }
    result
  }

  override def findSubway(subwayId: Long): ServiceResult[SubwayDTO] = {
    if(subwayId == null){
      return ServiceResult.notFound()
    }
    val subway:Subway = subwayDao.selectOne(new QueryWrapper[Subway]().
      eq("id",subwayId))

    if(subway == null){
      return ServiceResult.notFound()
    }
    ServiceResult.of(modelMapper.map(subway,classOf[SubwayDTO]))
  }

  override def findSubwayStation(stationId: Long): ServiceResult[SubwayStationDTO] = {
    if (stationId == null) return ServiceResult.notFound()
    val station = subwayStationDao.selectOne(new QueryWrapper[SubwayStation]().
      eq("subway_id",stationId))
    if (station == null) return ServiceResult.notFound()
    return ServiceResult.of(modelMapper.map(station, classOf[SubwayStationDTO]))
  }

  override def findCity(cityEnName: String): ServiceResult[SupportAddressDTO] = {
    if (cityEnName == null) return ServiceResult.notFound()

    val supportAddress = supportAddressDao.selectOne(new QueryWrapper[SupportAddress]().
      eq("level","city").eq("cn_name",cityEnName))
    if (supportAddress == null) return ServiceResult.notFound()
    val addressDTO = modelMapper.map(supportAddress, classOf[SupportAddressDTO])
    return ServiceResult.of(addressDTO)
  }


}
