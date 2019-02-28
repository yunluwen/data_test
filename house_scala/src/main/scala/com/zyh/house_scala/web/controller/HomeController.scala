package com.zyh.house_scala.web.controller

import com.zyh.house_scala.base.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{GetMapping, ResponseBody}

@Controller
class HomeController {

  @GetMapping(value = Array("/","/index"))
  def index(model: Model):String = {
    "index"
  }

  @GetMapping(Array("/404"))
  def notFoundPage():String = {
    "404"
  }

  @GetMapping(Array("/403"))
  def accessError():String = {
    "403"
  }

  @GetMapping(Array("/500"))
  def internalError():String = {
    "500"
  }

  @GetMapping(Array("/logout/page"))
  def logoutPage():String = {
    "logout"
  }


  @GetMapping(Array("/get"))
  @ResponseBody
  def get():ResponseEntity = {
    ResponseEntity.createBySuccessMessage("Success!")
  }

}
