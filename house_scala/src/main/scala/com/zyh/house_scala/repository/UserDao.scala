package com.zyh.house_scala.repository

import com.zyh.house_scala.entity.User
import org.apache.ibatis.annotations._

import scala.language.implicitConversions
import scala.collection.immutable.{List => _}

trait UserDao {

  @Insert(Array("insert into user(id,name)values(#{id},#{name})"))
  def insertUser(user:User) : Int

  @Update(Array("update user set name = #{name} where id = #{id}"))
  def updateUser(user:User) : Int

  @Delete(Array("delete from user where id = #{id}"))
  def deleteUser(@Param("id") id:Int) : Int

  @Select(Array("select id,name from user where id = #{id}"))
  def findUserById(@Param("id") id:Int) : User

}
