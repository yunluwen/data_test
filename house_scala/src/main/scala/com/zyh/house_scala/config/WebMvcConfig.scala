package com.zyh.house_scala.config

import com.zyh.house_scala.concurrent.{HttpFilter, HttpInterceptor}
import org.modelmapper.ModelMapper
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.servlet.config.annotation.{InterceptorRegistry, ResourceHandlerRegistry, WebMvcConfigurer}
import org.springframework.context.annotation.Bean

/**
  * springboot2的不同点
  */
@Configuration
class WebMvcConfig extends WebMvcConfigurer{

  /**
    * 静态资源加载配置
    * @param registry
    */
  override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
    registry.addResourceHandler("/static/**")
      .addResourceLocations("classpath:/static/")
  }

  /**
    * Bean Util
    * @return
    */
  @Bean def modelMapper = new ModelMapper

  /**
    * 添加一个拦截器
    * @param registry
    */
  override def addInterceptors(registry: InterceptorRegistry): Unit = {
    registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**")
  }

  //注册自定义的过滤器，这种增加过滤器的方式出现了问题，等待解决
//  @Bean def httpFilter():FilterRegistrationBean[HttpFilter] = {
//    val regist = new FilterRegistrationBean[HttpFilter]()
//    regist.setFilter(new HttpFilter())
//    regist.addUrlPatterns("/threadLocal/**")
//    regist
//  }

}
