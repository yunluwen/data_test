package com.zyh.house_scala.service.house

import com.zyh.house_scala.base.{ServiceMultiResult, ServiceResult}
import com.zyh.house_scala.web.dto.HouseDTO
import com.zyh.house_scala.web.form.{DataTableSearch, HouseForm, RentFilter}

trait HouseService {

  def save(houseForm: HouseForm):ServiceResult[HouseDTO]

  /**
    * 管理员查询列表
    * @param search
    * @return
    */
  //def adminQuery(search:DataTableSearch):ServiceMultiResult[HouseDTO]

  /**
    * 查询完整信息
    * @param id
    * @return
    */
  //def findCompleteOne(id:Long):ServiceResult[HouseDTO]

  /**
    * 查询信息集
    * @param rentFilter
    * @return
    */
  //def query(rentFilter: RentFilter):ServiceMultiResult[_]

  /**
    * 修改信息
    * @param houseForm
    * @return
    */
//  def update(houseForm: HouseForm):ServiceResult[_]
//
//  def removePhoto(id:Long):ServiceResult[_]
//
//  def updateCover(coverId:Long,targetId:Long):ServiceResult[_]
//
//  def removeTag(houseId:Long,tag:String):ServiceResult[_]
//
//  def updateStatus(id:Long,value:Int):ServiceResult[_]
//
//  def addTag(houseId:Long,tag:String):ServiceResult[_]

}
