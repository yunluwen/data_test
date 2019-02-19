package com.session.userquery

import com.session.constants.Constants
import com.session.utils.{AgeUtils, RDDUtils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}

import com.session.sessionpoly.SessionPoly;

/**
  * 用户查询操作
  * 根据用户的查询条件，一个或者多个：
  * 年龄范围，职业（多选），城市（多选），搜索词（多选），
  * 点击品类（多选）进行数据过滤，注意：session时间范围是必选的。
  * 返回的结果RDD元素格式:
  * key:sessionid
  * value:sessionid=value|searchword=value|clickcaterory=value|age=value|professional=value|city=value|sex=value
  */
object UserQuery {
  /**
    * param:sc:SparkContext
    * param:date:选定特定时间范围的session,String:2017-07-03，也已经默认交给SessionPoly处理，因而此处不填
    * param:searchword:关键词(多个),String:x1,x2,x3
    * param:clickcaterory:商品点击类(多个):String:x1,x2,x3
    * param:age:年龄范围,String:num-num
    * param:profession:职业(可选多个),String:x1,x2,x3
    * param:city:用户所在城市,String
    * return:RDD[(String,String)]
    * 其中taskid和date是必填选项，其余可以为空字符串:""
    */
  def userQuery(sc:SparkContext,date:String,searchword:String,
                clickcaterory:String, ages:String,
                professional:String, city:String):RDD[(String,String)]={
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    //判断age是否符合规范
    if(!AgeUtils.isFormat(ages)){
      println("年龄范围为不符合规范")
      null
    }else{
      /**
        * 获取session粒度聚合后的RDD
        * 对于RDD key-value对
        * value:sessionid=value|searchword=value|clickcaterory=value|age=value|professional=value|city=value|sex=value
        * 先用 | 分割，再通过 分割 = 获取对应数据的值
        */
      //获取SQLContext对象
      val sqlContext = new SQLContext(sc)
      val sessionPolyLines = SessionPoly.sessionPoly(sc,date)
      if(null==sessionPolyLines){
        println("session 获取失败")
        null
      }else{
        //处理session粒度聚合后的RDD
        val sessionRDD = sessionPolyLines.map(line=>{
          //用 | 分割value
          val sessionData = line._2.split("\\|")
          //获取sessionid
          val sessionid = sessionData(0).split("=")(1)
          //获取关键词
          val searchword = {
            //如果关键词项目为空，则返回空
            val words = sessionData(1).split("=")
            if(words.length == 1){
              ""
            }else{
              words(1)
            }
          }
          //获取商品点击类
          val clickcaterory = {
            //如果商品点击类项目为空，则返回空
            val caterory = sessionData(2).split("=")
            if(caterory.length == 1){
              ""
            }else{
              caterory(1)
            }
          }
          //获取用户年龄
          val age = sessionData(3).split("=")(1)
          //获取用户职业
          val professional = sessionData(4).split("=")(1)
          //获取用户所在城市
          val city = sessionData(5).split("=")(1)
          //获取用户性别
          val sex = sessionData(6).split("=")(1).split("\\)")(0)
          //返回Row，用于创建DataFrame
          (Row(sessionid, searchword, clickcaterory, Integer.valueOf(age), professional, city, sex))
        })
        /**
          * 创建schema
          * 即SQL数据类型，作为参数用于创建DataFrame
          */
        val sessionSchema=new StructType(Array(StructField(Constants.SESSION_ID,StringType, false),
          StructField(Constants.SESSION_SEARCHWORD, StringType,true),
          StructField(Constants.SESSION_CLICKCATERORY,StringType,true),
          StructField(Constants.USER_AGE, IntegerType, false),
          StructField(Constants.USER_PROFESSIONAL, StringType,true),
          StructField(Constants.USER_CITY,StringType,true),
          StructField(Constants.USER_SEX,StringType,true)))
        //利用SQLContext对象创建DataFrame，此为经过日期处理后的DataFrame
        val session_poly_DataFrame = sqlContext.createDataFrame(sessionRDD, sessionSchema)
        /**
          * Spark SQL语句处理用户查询操作
          * 1、通过判断年龄是否为空，选择是否进行年龄过滤操作
          * 2、通过判断职业是否为空，选择是否进行职业过滤操作
          * 3、通过判断城市是否为空，选择是否进行城市过滤操作
          * 4、通过判断关键词是否为空，选择是否进行关键词过滤操作
          * 5、通过判断点击的商品分类是否为空，选择是否进行点击的商品分类过滤操作
          */
        var userQueryDataFrame = session_poly_DataFrame
        //判断年龄是否为空，如果为空，不执行年龄过滤操作
        if(!ages.equals("")){
          //通过"-"分割年龄范围
          val age = ages.split("-")
          val num1 = Integer.valueOf(age(0))
          val num2 = Integer.valueOf(age(1))
          userQueryDataFrame = userQueryDataFrame.
            filter(Constants.USER_AGE + " > " + num1 + " and " +
              Constants.USER_AGE + " < " + num2)
        }
        //判断职业是否为空，如果为空，不执行职业过滤操作
        if(!professional.equals("")){
          //通过","分割出各个职业
          val professionals = professional.split(",")
          userQueryDataFrame = userQueryDataFrame.
            filter({
              /**
                * 通过字符串连接的方式，形成过滤操作
                */
              var filterStr = Constants.USER_PROFESSIONAL + " = '" + professionals(0) + "' "
              for(i <- 1 to (professionals.length-1)){
                filterStr = filterStr+" or " + Constants.USER_PROFESSIONAL + " = '"  + professionals(i) + "' "
              }
              filterStr
            })
        }
        //判断城市是否为空，如果为空，不执行城市过滤操作
        if(!city.equals("")){
          //通过","分割出各个城市
          val citys = city.split(",")
          userQueryDataFrame = userQueryDataFrame.
            filter({
              /**
                * 通过字符串连接的方式，形成过滤操作
                */
              var filterStr = Constants.USER_CITY + " = '" + citys(0) + "' "
              for(i <- 1 to (citys.length-1)){
                filterStr = filterStr+" or " + Constants.USER_CITY + " = '"  + citys(i) + "' "
              }
              filterStr
            })
        }
        //判断关键词是否为空，如果为空，不执行关键词过滤操作
        if(!searchword.equals("")){
          //通过","分割出各个关键词
          val searwords = searchword.split(",")
          userQueryDataFrame = userQueryDataFrame
            .filter({
              /**
                * 通过SQL语句中的like去匹配相应字符串
                */
              var filterStr = Constants.SESSION_SEARCHWORD + " like '%" + searwords(0) + "%' "
              for(i <- 1 to (searwords.length-1)){
                filterStr = filterStr+" or " + Constants.SESSION_SEARCHWORD + " like '%" + searwords(i) + "%' "
              }
              filterStr
            })
        }
        //判断点击的商品分类是否为空，如果为空，不执行点击的商品分类过滤操作
        if(!clickcaterory.equals("")){
          //通过","分割出各个点击的商品分类
          val caterory = clickcaterory.split(",")
          userQueryDataFrame = userQueryDataFrame
            .filter({
              /**
                * 通过SQL语句中的like去匹配相应字符串
                */
              var filterStr = Constants.SESSION_CLICKCATERORY + " like '%" + caterory(0) + "%' "
              for(i <- 1 to (caterory.length-1)){
                filterStr = filterStr+" or " + Constants.SESSION_CLICKCATERORY + " like '%" + caterory(i) + "%' "
              }
              filterStr
            })
        }
        //将DataFrame转换成RDD
        val result = RDDUtils.dataFrameToRDD(userQueryDataFrame)
        result
      }
    }
  }

}