package com.session.caterorysort

import com.session.constants.Constants
import org.apache.spark.SparkContext
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}

import com.session.userquery.UserQuery;

/**
  * 自定义二次排序key
  */
object ClickCaterorySort {
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
    * 由于事先并没有保存下单和支付商品分类
    * 因此这里考虑读取click.log文件，
    * 过滤掉不匹配筛选后的sessionid
    * 由数据可知，session中点击，下单，支付，未同时发生
    */
  def clickCaterorySort(sc:SparkContext,date:String,searchword:String,
                        clickcaterory:String, ages:String,
                        professional:String, city:String):Array[(Any,String)]={

    val userQueryArray = UserQuery.userQuery(sc,date,searchword,clickcaterory,ages,professional,city).
      map(line=>{
        val sessionid = "sessionid"
        //通过"|"分割，再用"="分割第一个字符串，即可获取sessionid
        val value = line._2.split("\\|")(0).split("=")(1)
        (sessionid,value)
      }).collect()
    //读取click log 数据，过滤不匹配用户选择的sessionid
    val sessionLines = sc.textFile(Constants.CLICK_LOG_PATH).filter(line => {
      /**
        * 先用"\t"分割字符串，
        * 获取sessionid,clickcaterory,ordercaterory,paycaterory
        */
      val lines = line.split("\t")
      //获取sessionid
      val sessionid = lines(2)
      val click = lines(6)
      val order = lines(8)
      val pay = lines(10)
      if(userQueryArray.contains(("sessionid", sessionid))){
        if(click.equals(" ") && order.equals(" ") && pay.equals(" ")){
          false
        }else{
          true
        }
      }else{
        false
      }
    }).map(line=>{
      /**
        * 先用"\t"分割字符串，
        * 获取clickcaterory,ordercaterory,paycaterory
        * 设置key-value格式
        * key：caterory:String
        * value:click|order|pay:String
        * 由于点击，下单，支付动作不可同时发生，因为一个session，只存在一个商品分类
        */
      var lines = line.split("\t")
      //获取cateroray
      val clickcateroray = lines(6)
      val ordercateroray = lines(8)
      val paycateroray = lines(10)
      //设置value
      var click = 0
      var order = 0
      var pay = 0
      var key = ""
      if(!clickcateroray.equals(" ")){
        key = clickcateroray
        click = 1
      }else if(!ordercateroray.equals(" ")){
        key = ordercateroray
        order = 1
      }else if(!paycateroray.equals(" ")){
        key = paycateroray
        pay = 1
      }
      //value由click,order,pay，组合而成
      val value = ""+click+"|"+order+"|"+pay
      (key,value)
    }).reduceByKey((x,y)=>{
      /**
        * 相同key的商品分类聚合
        * 先用","分割value
        * 获取click,order,pay的值，相加
        */
      val value_x = x.split("\\|")
      val value_y = y.split("\\|")
      //获取click,order,pay的值
      val click_x = value_x(0).toInt
      val order_x = value_x(1).toInt
      val pay_x = value_x(2).toInt
      val click_y = value_y(0).toInt
      val order_y = value_y(1).toInt
      val pay_y = value_y(2).toInt
      val click = click_x+click_y
      val order = order_x+order_y
      val pay = pay_x+pay_y
      //value由click,order,pay，组合而成
      val value = ""+click+"|"+order+"|"+pay
      value
    }).
      map(line=>{
        /**
          * 转换为利于Spark SQL语句使用的格式
          * Row(caterory, click, order, pay)
          */
        val caterory = line._1
        //分割获取商品点击、下单、支付至
        val values = line._2.split("\\|")
        Row(caterory, values(0).toInt, values(1).toInt, values(2).toInt)
      })
    /**
      * 创建session schema
      * 即SQL数据类型，作为参数用于创建DataFrame
      */
    val sessionSchema=new StructType(Array(StructField(Constants.SESSION_CATERORY,StringType, false),
      StructField(Constants.SESSION_CLICKCATERORY, IntegerType, false),
      StructField(Constants.SESSION_ORDERCATERORY, IntegerType,false),
      StructField(Constants.SESSION_PAYCATERORY,IntegerType,false)))
    /**
      * 获取SQLContext对象
      */
    val sqlContext = new SQLContext(sc)
    val sessionDataFrame = sqlContext.createDataFrame(sessionLines, sessionSchema)
    /**
      * 按优先级对商品分类进行排序
      * click,order,pay
      */
    val result = sessionDataFrame.orderBy(
      sessionDataFrame(Constants.SESSION_CLICKCATERORY).desc,
      sessionDataFrame(Constants.SESSION_ORDERCATERORY).desc,
      sessionDataFrame(Constants.SESSION_PAYCATERORY).desc)
    //重新转换成RDD格式，并获取前10个热门品类
    val top10 = result.rdd.map(line=>{
      //获取商品分类
      val caterory = line(0)
      //获取点击数
      val click = line(1)
      //获取下单数
      val order = line(2)
      //获取支付数
      val pay = line(3)
      /**
        * 映射成key-value格式
        * key：caterory:String
        * value:click|order|pay:String
        */
      (caterory, ""+click+"|"+order+"|"+pay)
    }).take(10)
    top10
  }

}