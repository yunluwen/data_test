package com.zyh.utils

import com.google.gson.Gson
/**
  * 谷歌的Gson和阿里的fastJson不冲突
  * Json工具类： scala使用谷歌的Gson
  *            java使用阿里的fastjson
  */
class JsonUtils {

  //注意，case class不要定义在方法内部
  case class Student( name:String , no: String )
  /**
    * 测试，对象转变为json
    */
  def objectToJson(): Unit ={

    val gson = new Gson
    val student = Student("张三", "100")
    val str = gson.toJson(student, classOf[Student])
    println(str)

    val student2 = gson.fromJson(str, classOf[Student])
    println(student2)

  }

}

object JsonUtils{
  val jsonUtils = new JsonUtils
  def main(args: Array[String]): Unit = {
    jsonUtils.objectToJson()
  }
}
