package com.zyh.house_scala.web.controller.admin

import java.io.{File, IOException}
import com.google.gson.Gson
import com.zyh.house_scala.base.{DataTablesResponse, ResponseEntity, ServiceMultiResult, StatusCode}
import com.zyh.house_scala.service.house.HouseService
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.{IncorrectCredentialsException, UnknownAccountException, UsernamePasswordToken}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import com.zyh.house_scala.web.form.DataTableSearch
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile

/**
  * 后台管理
  */
@Controller
class AdminController @Autowired() (gson:Gson){

  @GetMapping(Array("/admin/center"))
  def adminCenterPage(): String ={
    "admin/center"
  }

  @GetMapping(Array("/admin/welcome"))
  def welcomePage(): String ={
    "admin/welcome"
  }

  @GetMapping(Array("/admin/login"))
  def login(): String ={
    "admin/login"
  }

  @GetMapping(Array("/admin/add/house"))
  def addHousePage(): String ={
    "admin/house-add"
  }

  @GetMapping(Array("/admin/house/list"))
  def houseListPage(): String ={
    "admin/house-list"
  }

  @PostMapping(Array("/admin/login"))
  def login(username: String,password:String,model: Model):String = {
    val subject = SecurityUtils.getSubject
    val token = new UsernamePasswordToken(username,password)
    try{
      subject.login(token)
      "redirect:/admin/center"
    }catch {
      //登录失败
      case e:UnknownAccountException => {
        model.addAttribute("msg","用户名不存在")
        "admin/login"
      }
      case e:IncorrectCredentialsException => {
        model.addAttribute("msg","密码不正确")
        "admin/login"
      }
    }
  }

  @GetMapping(Array("/admin/logout"))
  def logout():String = {
    "redirect:/admin/login"
  }

  @ResponseBody
  @PostMapping(Array("admin/houses"))
  def houses(@ModelAttribute search: DataTableSearch):DataTablesResponse = {
//    val result:ServiceMultiResult[] = houseService.adminQuery(search)
//    val data = result.result
//    val dataTablesResponse:DataTablesResponse =
//      new DataTablesResponse(StatusCode.SUCCESS.id,data)
//    dataTablesResponse.recordsFiltered = result.total
//    dataTablesResponse.recordsTotal = result.total
//    dataTablesResponse.draw = search.draw
//    dataTablesResponse
    null
  }

  /**
    * 上传图片接口
    */
  @PostMapping(value = Array("admin/upload/photo"),
    consumes = Array(MediaType.MULTIPART_FORM_DATA_VALUE))
  @ResponseBody
  def uploadPhoto(@RequestParam("file") file:MultipartFile):ResponseEntity = {
    if (file.isEmpty) {
      return ResponseEntity.createByErrorCodeMessage(
        StatusCode.NOT_VALID_PARAM.id, StatusCode.NOT_VALID_PARAM.toString)
    }
    val fileName:String = file.getOriginalFilename
    val target:File = new File(
      "/Users/zhangyunhao/IdeaProjects/house_scala/tmp/",fileName)
    try {
      file.transferTo(target)
      ResponseEntity.createBySuccess
    }catch {
      case e:IOException => ResponseEntity
        .createByErrorCodeMessage(
        StatusCode.BAD_REQUEST.id, StatusCode.BAD_REQUEST.toString)
    }
  }









}
