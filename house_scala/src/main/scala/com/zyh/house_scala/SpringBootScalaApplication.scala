package com.zyh.house_scala    //注意这里，一定要有包名

import com.zyh.house_scala.concurrent.{HttpFilter, HttpInterceptor}
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.web.servlet.config.annotation.InterceptorRegistry

@Configuration
@EnableAutoConfiguration     //开启自动配置功能
@ComponentScan               //默认启动当前包下面的启动对象，可以手动设置
@MapperScan(Array("com.zyh.house_scala.repository"))  //注意这里，需要配置扫描mapp的文件
@SpringBootApplication
class Config

//scala结合springboot
object SpringBootScalaApplication extends App {

  SpringApplication.run(classOf[Config])


}


