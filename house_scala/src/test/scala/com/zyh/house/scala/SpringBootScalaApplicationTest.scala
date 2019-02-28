package com.zyh.house.scala

import com.zyh.house_scala.base.StatusCode
import org.junit.runner.RunWith
import org.junit.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[SpringRunner]))
class SpringBootScalaApplicationTest {

  val logger = LoggerFactory.getLogger(getClass)

  @Test
  def loggerTest(): Unit ={
    logger.trace("trace日志")
    logger.debug("debug日志")
    logger.info("info日志")
    logger.warn("warn信息")
    logger.error("error信息")
  }

  @Test
  def enumTest():Unit = {
    val code = StatusCode
    println(code.SUCCESS)
  }

}
