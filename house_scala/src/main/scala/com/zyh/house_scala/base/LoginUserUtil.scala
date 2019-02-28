package com.zyh.house_scala.base

import com.zyh.house_scala.entity.SysUser
import org.apache.shiro.SecurityUtils

class LoginUserUtil {

  /**
    * 获取当前登录用户
    * @return
    */
  private def load():SysUser = {
    val principal:SysUser = SecurityUtils.getSubject.getPrincipal.asInstanceOf[SysUser]
    if(principal != null && principal.isInstanceOf[SysUser]){
      return principal
    }
    null
  }

}

object LoginUserUtil {

  val loginUserUtil:LoginUserUtil = new LoginUserUtil
  /**
    * 获取当前登录用户的id
    */
  def getLoginUserId():Long = {
    val user:SysUser = loginUserUtil.load()
    if(user == null){
      return -1L
    }
    user.id
  }
}


