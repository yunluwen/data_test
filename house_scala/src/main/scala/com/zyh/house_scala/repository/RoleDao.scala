package com.zyh.house_scala.repository

import com.zyh.house_scala.entity.Role
import java.util.{List => JavaList}

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.{Param, Select}

import scala.collection.immutable.{List => _}
import scala.language.implicitConversions

trait RoleDao extends BaseMapper[Role]{

  @Select(Array("select * from role where userid = #{userId}"))
  def findRolesByUserId(@Param("userId") userId:Long): JavaList[Role]

}
