package com.zyh.house_scala.security

import com.zyh.house_scala.entity.{SysUser,Role}
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.zyh.house_scala.repository.{RoleDao, SysUserDao}
import org.apache.shiro.authc.{AuthenticationInfo, AuthenticationToken}
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.springframework.beans.factory.annotation.Autowired


/**
  * 自定义系统用户Realm
  * 注意加载外部bean的时候，需要将加载的类写到父类的前面
  */
class SysUserRealm @Autowired() (sysUserDao:SysUserDao,roleDao: RoleDao) extends AuthorizingRealm {

  /**
    * 认证
    * @param authenticationToken
    * @return
    */
  override def doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo = {
    //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
    if (authenticationToken.getPrincipal == null) return null
    //获取用户信息//获取用户信息
    val name = authenticationToken.getPrincipal.toString
    val user = sysUserDao.selectOne(new QueryWrapper[SysUser]().eq("name",name))
    if (user == null) { //这里返回后会报出对应异常
      return null
    }
    //这里验证authenticationToken和simpleAuthenticationInfo的信息
    new SimpleAuthenticationInfo(name, user.password.toString, getName)
  }

  /**
    * 授权
    * @param principalCollection
    * @return
    */
  override def doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo = {
    //给用户添加权限
    val info = new SimpleAuthorizationInfo()
    //获取登录用户名
    val name= principalCollection.getPrimaryPrincipal.toString
    val dbUser = sysUserDao.selectOne(new QueryWrapper[SysUser]().eq("name",name))
    val role = roleDao.selectOne(new QueryWrapper[Role]().eq("user_id",dbUser.id))
    info.addStringPermission(role.name)
    info
  }

}
