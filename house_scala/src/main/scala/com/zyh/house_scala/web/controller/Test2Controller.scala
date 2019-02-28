package com.zyh.house_scala.web.controller

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.{IncorrectCredentialsException, UnknownAccountException, UsernamePasswordToken}
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{GetMapping, PostMapping}

/**
  * 测试控制器
  */
@Controller
class Test2Controller {

  @GetMapping(Array("/tologin"))
  def login():String = {
    "test/login"
  }

  @GetMapping(Array("/index"))
  def index():String = {
    "test/index"
  }

  @GetMapping(Array("/insert"))
  def insert():String = {
    "test/insert"
  }

  @GetMapping(Array("/update"))
  def update():String = {
    "test/update"
  }

  @GetMapping(Array("/unAuth"))
  def unAuth():String = {
    "test/unAuth"
  }

  @PostMapping(Array("login"))
  def login(name:String,password:String,model: Model):String = {
    //使用shiro编写认证操作
    //1:获取subject
    val subject = SecurityUtils.getSubject
    //2:封装用户数据
    val token = new UsernamePasswordToken(name,password)
    //3:执行登录方法
    try{
      subject.login(token)
      //登录成功
      "redirect:/insert"      //跳转url
    }catch {
      //登录失败
      case e:UnknownAccountException => {
        model.addAttribute("msg","用户名不存在")
        "test/login"
      }
      case e:IncorrectCredentialsException => {
        model.addAttribute("msg","密码不正确")
        "test/login"
      }
    }
  }



}
