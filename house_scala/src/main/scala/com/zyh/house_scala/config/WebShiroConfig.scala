package com.zyh.house_scala.config

import java.util

import com.zyh.house_scala.repository.{RoleDao, SysUserDao}
import com.zyh.house_scala.security.SysUserRealm
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * 权限
  */
//@Configuration
class WebShiroConfig @Autowired() (sysUserDao:SysUserDao,roleDao: RoleDao){

  @Bean
  def getshiroFilterFactoryBean(@Qualifier("securityManager")securityManager:
                                DefaultWebSecurityManager):ShiroFilterFactoryBean = {
    val shiroFilterFactoryBean = new ShiroFilterFactoryBean()

    shiroFilterFactoryBean.setSecurityManager(securityManager)
    val filterMap = new util.LinkedHashMap[String,String]
    filterMap.put("/user/login","anon")   //放行login接口
    filterMap.put("/admin/login","anon")

    //授权过滤器
    //注意：当前授权拦截后，shiro会自动跳转到未授权页面
    filterMap.put("/admin/*","perms[ADMIN]")

    filterMap.put("/*","authc")
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap)

    //修改跳转的登录页面
    shiroFilterFactoryBean.setLoginUrl("/user/login")
    //设置未授权提示页面
    shiroFilterFactoryBean.setUnauthorizedUrl("/403")

    shiroFilterFactoryBean
  }

  @Bean(name = Array("securityManager"))
  def getDefaultWebSecurityManager(@Qualifier("sysUserRealm")userRealm: SysUserRealm):
  DefaultWebSecurityManager ={
    val securityManager = new DefaultWebSecurityManager()
    //关联realm
    securityManager.setRealm(userRealm)
    securityManager
  }


  @Bean(name = Array("sysUserRealm"))
  def getSysUserRealm():SysUserRealm = {
    new SysUserRealm(sysUserDao,roleDao)
  }

}
