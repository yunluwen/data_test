package com.zyh.house_scala.config

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.transaction.annotation.EnableTransactionManagement


@EnableTransactionManagement
@Configuration
class MybatisPlusConfig {

  /**
    * 配置mybatis plus分页插件
    * @return
    */
  @Bean
  def paginationInterceptor(): PaginationInterceptor = new PaginationInterceptor

}
