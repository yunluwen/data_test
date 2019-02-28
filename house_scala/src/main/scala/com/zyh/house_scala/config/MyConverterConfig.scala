package com.zyh.house_scala.config

import com.zyh.house_scala.entity.User
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter

import scala.language.implicitConversions
import scala.util.{Failure, Random, Success, Try}

/**
  * 自定义格式化器      //有问题，待解决
  */
//@Configuration      //注意自定义的配置需要这个标签来将配置装载到spring容器中
class MyConverterConfig extends Converter[String,User]{
  /**
    * 1、extends实现Java接口
    * 2、覆盖Java的抽象方法
    * 3、override可选，入参和返回值必须相同
    * 4、如果方法访问权限是protected则可能需要使用Scala包级访问控制 protected[packageName]
    */
  //将字符串转化为用户对象
  override def convert(s: String): User = {
    println(s)
    val info = s.split(":")
    println(info.size)
    val user: User = null
    user.id = Random.nextInt(100)
    if(info.size == 2){
      user.name = info(1)
      return user
    }
    null
  }

}


