package com.zyh.house_scala.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.zyh.house_scala.repository.UserDao
import com.zyh.house_scala.entity.User

import scala.language.implicitConversions

@Service
class UserService @Autowired() (userDao:UserDao){

  //根据用户id查找用户姓名
  val findUserById = (id:Int) => userDao.findUserById(id)
  //添加用户信息
  val insertUser = (user:User) => userDao.insertUser(user)

  val updateUser = (user:User) => userDao.updateUser(user)

  val deleteUser = (id:Int) => userDao.deleteUser(id)

}
