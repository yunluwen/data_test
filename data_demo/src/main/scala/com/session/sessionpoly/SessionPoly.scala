package com.session.sessionpoly

import com.session.constants.Constants
import com.session.utils.{DateUtils, RDDUtils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}


object SessionPoly {
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
//  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
  /**
    * param:SparkContext
    * param:date日期：2017-07-31
    * return:RDD[(String,String)]
    */
  def sessionPoly(sc:SparkContext,date:String): RDD[(String,String)] ={

    //先判断日期是否符合规范
    if(!DateUtils.isFormat(date)){
      //日期不符合规范
      println("日期不符合规范!")
      null
    }else{
      //读取click log 数据，进行session数据聚合
      val sessionLines = sc.textFile(Constants.CLICK_LOG_PATH)
      /**
        * 先处理数据，再使用Spark SQL
        * 获取session 中可用信息
        * userID, sessionid, searchword, clickcateroryid,
        */
      val session_poly = sessionLines.filter(_.split("\t")(0).equals(date)).map(line => {
        //分割字符，获取每项数据
        val clickData = line.split("\t")
        /**
          * key-value格式设置
          * key:sessionid: String
          * value:Array(userID,keywords,clickCategoryID)
          */
        //首先获取sessionid
        val sessionid = clickData(2)
        val value = Array(clickData(1), clickData(5), clickData(6))
        //返回key-value类型
        (sessionid, value)
      }).reduceByKey((x, y) => {
        //获取userID
        val userID = x(0)
        //设置关键词
        val keywords = x(1)+","+y(1)
        //设置商品类
        val caterory = x(2)+","+y(2)
        val value = Array(userID, keywords, caterory)
        value
      }).map(line=>{
        //处理关键词
        val keywords = line._2(1).split(",").filter(line => (!(line.equals("") || line.equals(" ")))).mkString(",")
        //处理商品分类
        val caterory = line._2(2).split(",").filter(line => (!(line.equals("") || line.equals(" ")))).mkString(",")
        val sessionid = line._1
        val value = Array()
        Row(sessionid, line._2(0), keywords, caterory)
      })

      //获取本地user表
      val userLines = sc.textFile(Constants.USER_PATH)
      val user_poly = userLines.map(line => {
        /**
          * key-value格式设置
          * key:userid
          * value:Array(age, professional, city, sex)
          */
        //分割line
        val userData = line.split(" ")
        //获取用户id
        val userID = userData(0)
        //设置value
        val value = Array(userData(3), userData(4), userData(5), userData(6))
        //返回key-value
        (userID, value)
      }).map(line=>Row(line._1,line._2(0),line._2(1),line._2(2),line._2(3)))

      /**
        * 获取SQLContext对象，对user和session 进行join 操作
        */
      val sqlContext = new SQLContext(sc)
      /**
        * 创建session schema
        * 即SQL数据类型，作为参数用于创建DataFrame
        */
      val sessionSchema=new StructType(Array(StructField(Constants.SESSION_ID,StringType, false),
        StructField(Constants.USER_ID, StringType, false),
        StructField(Constants.SESSION_SEARCHWORD, StringType,true),
        StructField(Constants.SESSION_CLICKCATERORY,StringType,true)))
      val sessionDataFrame = sqlContext.createDataFrame(session_poly, sessionSchema)
      /**
        * 创建user schema
        * 即SQL数据类型，作为参数用于创建DataFrame
        */
      val userSchema=new StructType(Array(StructField(Constants.USER_ID,StringType, false),
        StructField(Constants.USER_AGE, StringType, false),
        StructField(Constants.USER_PROFESSIONAL, StringType,true),
        StructField(Constants.USER_CITY,StringType,true),
        StructField(Constants.USER_SEX,StringType,true)))
      val userDataFrame = sqlContext.createDataFrame(user_poly, userSchema)
      val user_session = sessionDataFrame.join(userDataFrame, Constants.USER_ID).select(Constants.SESSION_ID,
        Constants.SESSION_SEARCHWORD,
        Constants.SESSION_CLICKCATERORY, Constants.USER_AGE,
        Constants.USER_PROFESSIONAL, Constants.USER_CITY, Constants.USER_SEX)
      /**
        * 将DataFrame转换成RDD
        * 再将RDD转换成key-value格式
        * key: sessionid
        * value:sessionid=value|searchword=value|clickcaterory=value|age=value|professional=value|city=value|sex=value
        */
      val result = RDDUtils.dataFrameToRDD(user_session)
      result
    }
  }

}
