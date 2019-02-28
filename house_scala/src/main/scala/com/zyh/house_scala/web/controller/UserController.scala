package com.zyh.house_scala.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import com.zyh.house_scala.service.UserService
import com.zyh.house_scala.entity.User

@RestController
@RequestMapping(Array("/test_user"))    //注意在scala里面的写法
class UserController @Autowired() (userService:UserService){

  @GetMapping(Array("/info"))      //Restful风格的api
  def findById(id:Int): User ={
    val user = userService.findUserById(id)
    user
  }

  @PostMapping(Array("/info"))
  def insert(user:User): Boolean ={
    if(userService.insertUser(user) > 0){
      return true
    }
    false
  }

  @PutMapping(Array("/info"))
  def update(user:User):Boolean = {
    if(userService.updateUser(user) > 0){
      return true
    }
    false
  }

  @DeleteMapping(Array("/info"))
  def delete(id:Int):Boolean = {
    if(userService.deleteUser(id) > 0){
      return true
    }
    false
  }

  @GetMapping(Array("/exception"))   //检验异常处理机制
  def exception(): Unit ={
    throw new RuntimeException("hello error")
  }



}
