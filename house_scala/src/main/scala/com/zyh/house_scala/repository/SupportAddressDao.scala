package com.zyh.house_scala.repository

import java.util.{List => JavaList}

import scala.collection.immutable.{List => _}
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zyh.house_scala.entity.SupportAddress
import com.zyh.house_scala.web.dto.SupportAddressDTO

trait SupportAddressDao extends BaseMapper[SupportAddress] {

  def findAllCities():JavaList[SupportAddressDTO]

  def findAllByLevel(level:String):JavaList[SupportAddressDTO]

}
