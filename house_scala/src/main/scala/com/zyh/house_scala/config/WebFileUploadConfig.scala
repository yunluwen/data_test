package com.zyh.house_scala.config

import com.qiniu.common.Zone
import com.google.gson.Gson
import com.qiniu.storage.BucketManager
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import org.springframework.beans.factory.annotation.Value
import javax.servlet.MultipartConfigElement
import org.springframework.boot.autoconfigure.condition.{ConditionalOnClass, ConditionalOnMissingBean, ConditionalOnProperty}
import org.springframework.boot.autoconfigure.web.ServerProperties.Servlet
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.support.{StandardMultipartHttpServletRequest, StandardServletMultipartResolver}
import org.springframework.web.servlet.DispatcherServlet

/**
  * 文件上传配置
  */
@Configuration
@ConditionalOnClass(Array(classOf[Servlet], classOf[StandardMultipartHttpServletRequest],
  classOf[MultipartConfigElement]))
@ConditionalOnProperty(prefix = "spring.http.multipart", name =Array("enabled"), matchIfMissing = true)
@EnableConfigurationProperties(Array(classOf[MultipartProperties]))
class WebFileUploadConfig {

  var multipartProperties = new MultipartProperties

  def this(multipartProperties: MultipartProperties) {
    this()
    this.multipartProperties = multipartProperties
  }

  /**
    * 上传配置
    */
  def multipartConfigElement():MultipartConfigElement = {
    this.multipartProperties.createMultipartConfig()
  }

  /**
    * 注册解析器
    */
  @Bean(name= Array(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME))
  @ConditionalOnMissingBean(Array(classOf[MultipartResolver]))
  def multipartResolver():StandardServletMultipartResolver = {
    val multipartResolver = new StandardServletMultipartResolver
    multipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily)
    return multipartResolver
  }

  /**
    * 华东机房
    * @return
    */
  @Bean
  def qiniuConfig():com.qiniu.storage.Configuration = {
    return new com.qiniu.storage.Configuration(Zone.zone0())
  }

  /**
    * 构建一个七牛上传工具实例
    */
  @Bean def uploadManager = new UploadManager(qiniuConfig)

  //加载配置文件的配置
  @Value("${qiniu.AccessKey}")
  var accessKey:String = ""
  @Value("${qiniu.SecretKey}")
  var secretKey:String = ""

  /**
    * 认证信息实例
    * @return
    */
  @Bean def auth: Auth = Auth.create(accessKey, secretKey)

  /**
    * 构建七牛空间管理实例
    */
  @Bean def bucketManager = new BucketManager(auth, qiniuConfig)

  @Bean def gson = new Gson

}















