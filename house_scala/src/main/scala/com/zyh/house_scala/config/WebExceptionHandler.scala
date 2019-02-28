package com.zyh.house_scala.config

import org.springframework.core.convert.ConversionFailedException
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler}

/**
  * 异常处理器
  */
@ControllerAdvice //加上@ControllerAdvice注解将会处理controller层抛出的对应的异常
class WebExceptionHandler {

  /**
    * scala的classOf的作用:通过类名获取类对象，相当于java的 类名.class
    * 对于对象，可以通过 对象名.getClass() 来获取类对象
    * @param exception
    * @return
    */
  @ExceptionHandler(Array(classOf[RuntimeException],    //可以设置自定义异常
    classOf[NullPointerException],classOf[ConversionFailedException]))
  def handleException(exception: Exception): String ={
    exception.toString+"\tlalala..."
  }

}
