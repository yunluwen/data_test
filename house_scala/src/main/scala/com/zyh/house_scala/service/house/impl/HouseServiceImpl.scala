package com.zyh.house_scala.service.house.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.google.common.collect.Lists
import com.zyh.house_scala.entity.House
import com.zyh.house_scala.entity.HousePicture
import com.zyh.house_scala.entity.HouseTag
import com.zyh.house_scala.web.dto.HouseDTO
import com.zyh.house_scala.web.dto.HouseDetailDTO
import com.zyh.house_scala.web.dto.HousePictureDTO
import java.util.Date
import java.util

import scala.collection.immutable.{List => _}
import scala.language.implicitConversions
import com.zyh.house_scala.base.{LoginUserUtil, ServiceMultiResult, ServiceResult}
import com.zyh.house_scala.entity.{HouseDetail, Subway, SubwayStation}
import com.zyh.house_scala.repository._
import com.zyh.house_scala.service.house.HouseService
import com.zyh.house_scala.web.form.{DataTableSearch, HouseForm, RentFilter}
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HouseServiceImpl @Autowired() (modelMapper: ModelMapper, subwayDao: SubwayDao,
                                     subwayStationDao: SubwayStationDao,
                                     houseDao: HouseDao,
                                     houseDetailDao: HouseDetailDao,
                                     housePictureDao: HousePictureDao)
  extends HouseService{

  override def save(houseForm: HouseForm): ServiceResult[HouseDTO] = {

    val houseDetail:HouseDetail = new HouseDetail
    val subwayValidtionResult:ServiceResult[HouseDTO] = wrapperDetailInfo(houseDetail, houseForm)
    if (subwayValidtionResult != null) return subwayValidtionResult

    val house = new House
    modelMapper.map(houseForm,house)

    val now = new Date()
    house.createTime = now
    house.lastUpdateTime = now
    house.adminId = LoginUserUtil.getLoginUserId()
    houseDao.insert(house)       //mybatis plus实现jpa的save方法

    val detail = new HouseDetail
    detail.houseId = house.id


    null
  }


  private def wrapperDetailInfo(houseDetail: HouseDetail, houseForm: HouseForm): ServiceResult[HouseDTO] = {
    val subway = subwayDao.selectOne(new QueryWrapper[Subway]().eq("id",houseForm.subwayLineId))
    if (subway == null) return ServiceResult.notFound()
    val subwayStation = subwayStationDao.selectOne(new QueryWrapper[SubwayStation]().
      eq("id",houseForm.subwayStationId))
    //if (subwayStation == null || subway.id.ne(subwayStation.subwayId)) return ServiceResult.notFound()
    houseDetail.subwayLineId = subway.id
    houseDetail.subwayLineName = subway.name
    houseDetail.subwayStationId = subwayStation.id
    houseDetail.subwayStationName = subwayStation.name
    houseDetail.description = houseForm.description
    houseDetail.detailAddress = houseForm.detailAddress
    houseDetail.layoutDesc = houseForm.layoutDesc
    houseDetail.rentWay = houseForm.rentWay
    houseDetail.roundService = houseForm.roundService
    houseDetail.traffic = houseForm.traffic
    null
  }








//  /**
//    * 列表页面
//    * @param search
//    * @return
//    */
//  override def adminQuery(search: DataTableSearch): ServiceMultiResult = {
//    val houseDTOs = Lists.newArrayList()
//    val page = search.start / search.length   //第几页
//
//    null
//  }
//
//  override def findCompleteOne(id: Long): ServiceResult = ???
//
//  override def query(rentFilter: RentFilter): ServiceMultiResult = ???
//
//  override def update(houseForm: HouseForm): ServiceResult = ???
//
//  override def removePhoto(id: Long): ServiceResult= ???
//
//  override def updateCover(coverId: Long, targetId: Long): ServiceResult = ???
//
//  override def removeTag(houseId: Long, tag: String): ServiceResult = ???
//
//  override def updateStatus(id: Long, value: Int): ServiceResult = ???
//
//  override def addTag(houseId: Long, tag: String): ServiceResult = ???
}
