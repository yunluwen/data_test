package com.zyh.house_scala.config

import java.util

import com.zyh.house_scala.security.UserRealm
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * shiro配置
  */
//@Configuration
class MyShiroConfig {

  /**
    * 创建shiroFilterFactoryBean
    */
  @Bean
  def getshiroFilterFactoryBean(@Qualifier("securityManager")securityManager:
                                DefaultWebSecurityManager):ShiroFilterFactoryBean = {
    val shiroFilterFactoryBean = new ShiroFilterFactoryBean()

    //设置安全管理器
    shiroFilterFactoryBean.setSecurityManager(securityManager)
    //添加内置过滤器
    /**
      * shiro内置过滤器，可以实现权限相关的拦截
      *   常用的过滤器：
      *     anon:无需认证(登录)可以访问
      *     authc:必须认证才可以访问
      *     user:如果使用rememberMe的功能，可以直接访问
      *     perms:该资源必须得到资源权限才可以访问
      *     role:该资源必须得到角色权限才可以访问
      */
    val filterMap = new util.LinkedHashMap[String,String]
//    filterMap.put("/insert","authc")
//    filterMap.put("/update","authc")
    filterMap.put("/index","anon")
    filterMap.put("/login","anon")   //放行login接口

    //授权过滤器
    //注意：当前授权拦截后，shiro会自动跳转到未授权页面
    filterMap.put("/insert","perms[user:insert]")  //中括号内的是授权字符串

    filterMap.put("/*","authc")
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap)

    //修改跳转的登录页面
    shiroFilterFactoryBean.setLoginUrl("/tologin")
    //设置未授权提示页面
    shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth")

    shiroFilterFactoryBean
  }


  /**
    * 创建DefaultWebSecurityManager
    */
  @Bean(name = Array("securityManager"))
  def getDefaultWebSecurityManager(@Qualifier("userRealm")userRealm: UserRealm):
  DefaultWebSecurityManager ={
    val securityManager = new DefaultWebSecurityManager()
    //关联realm
    securityManager.setRealm(userRealm)
    securityManager
  }

  /**
    * 创建Realm
    */
  @Bean(name = Array("userRealm"))
  def getRealm():UserRealm = {
    new UserRealm()
  }

}
