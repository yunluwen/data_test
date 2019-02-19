package com.session.utils

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame

object RDDUtils {
  /**
    * 将DataFrame转换成RDD
    * 再将RDD转换成key-value格式
    * key: sessionid
    * value:sessionid=value|searchword=value|clickcaterory=value|age=value|professional=value|city=value|sex=value
    */
  def dataFrameToRDD(dataFrame:DataFrame): RDD[(String,String)] ={
    val result = dataFrame.rdd.map(line=>{
      //获取sessionid
      val sessionid = line(0)+""
      //获取关键词
      val searchword = line(1)
      //获取商品点击分类
      val caterory = line(2)
      //获取用户年龄
      val age = line(3)
      //获取用户职业
      val professional = line(4)
      //获取用户所在城市
      val city = line(5)
      //获取用户性别
      val sex = line(6)
      //设置value
      val value = "sessionid="+sessionid+"|searchword="+
        searchword+"|clickcaterory="+caterory+"|age="+age+
        "|professional="+professional+"|city="+city+"|sex="+sex
      (sessionid, value)
    })
    result
  }
}