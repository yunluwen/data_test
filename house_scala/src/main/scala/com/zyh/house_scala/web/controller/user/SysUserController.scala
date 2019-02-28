package com.zyh.house_scala.web.controller.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping}
import scala.language.implicitConversions

/**
  * 用户进入系统的入口
  */
@Controller
@RequestMapping(Array("/user"))
class SysUserController {

  @GetMapping(Array("/login")) def loginPage = "user/login"

  @GetMapping(Array("/center")) def centerPage = "user/center"

}
