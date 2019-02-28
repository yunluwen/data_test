package com.zyh.house_scala.security

import org.apache.shiro.authc.{AuthenticationInfo, AuthenticationToken, SimpleAuthenticationInfo, UsernamePasswordToken}
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

/**
  * 自定义Realm
  */
class UserRealm extends AuthorizingRealm {

  /**
    * 执行认证逻辑
    * @param authenticationToken
    * @return
    */
  override def doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo = {
    println("执行认证逻辑")
    //假设数据库的用户名和密码
    val name = "zhangyunhao"
    val password = "123456"
    //编写shiro判断逻辑，判断用户名和密码
    //1、判断用户名
    val token = authenticationToken.asInstanceOf[UsernamePasswordToken]
    if(!token.getUsername.equals(name)){
      //用户名不存在
      return null    //shiro底层会抛出UnknownAccountException,这个return不能省略
    }

    //2、判断密码
    new SimpleAuthenticationInfo("",password,"")
  }

  /**
    * 执行授权逻辑
    * @param principalCollection
    * @return
    */
  override def doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo = {
    println("执行授权逻辑")
    //给资源进行授权
    val info = new SimpleAuthorizationInfo()
    //添加资源的授权字符串,注意需要和授权过滤器的授权字符串保持一致
    //用户的授权字符串通常存储在数据库中
    info.addStringPermission("user:insert")  //硬编码，不推荐
    info
  }

}
