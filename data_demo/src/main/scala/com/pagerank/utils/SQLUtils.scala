package com.pagerank.utils

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * sqlContext
  * @param sc
  */
class SQLUtils private(val sc : SparkContext) {

}

object SQLUtils {

   def getRDDData(sc : SparkContext,fileName : String): SQLContext ={
    val sqlUtils = new SQLUtils(sc)
    val sqlContext = new SQLContext(sc)
    if(fileName.equals("user_info.txt")) {
      val data = sc.textFile(PathUtils.getFilePath(fileName)).map(_.split(" "))
      val dataRow = data.map(user => Row(user(0),user(1),user(2),user(3).trim.toInt,user(4),user(5),user(6)))
      val schema = StructType(StructField("session",StringType,true) ::
        StructField("userId",StringType,true) ::
        StructField("userName",StringType,true) ::
        StructField("age",IntegerType,true) ::
        StructField("professional",StringType,true) ::
        StructField("city",StringType,true) ::
        StructField("sex",StringType,true) :: Nil)

      val userInfo = sqlContext.createDataFrame(dataRow,schema)
      userInfo.registerTempTable("user_info")
      sqlContext

    } else if(fileName.equals("product.txt")) {
      val data = sc.textFile(PathUtils.getFilePath(fileName)).map(_.split("\t"))
      val dataRow = data.map(product => Row(product(0),product(1),product(2)))
      val schema = StructType(StructField("productId",StringType,true) ::
        StructField("productName",StringType,true) ::
        StructField("status",StringType,true) :: Nil)

      val userInfo = sqlContext.createDataFrame(dataRow,schema)
      userInfo.registerTempTable("product_info")
      sqlContext
    }

     val data = sc.textFile(PathUtils.getFilePath(fileName)).map(_.split("\t"))
     val dataRow = data.map(action => Row(action(0),action(1),action(2),action(3),action(4),action(5),action(6)))
     val schema = StructType(StructField("date",StringType,true) ::
       StructField("userId",StringType,true) ::
       StructField("sessionId",StringType,true) ::
       StructField("pageId",IntegerType,true) ::
       StructField("actionTime",StringType,true) ::
       StructField("searchKeyword",StringType,true) ::
       StructField("clickCategoryId",StringType,true) ::
       StructField("clickProductId",StringType,true) ::
       StructField("orderCategoryIds",IntegerType,true) ::
       StructField("orderProductIds",StringType,true) ::
       StructField("payCategoryIds",StringType,true) ::
       StructField("payProductIds",StringType,true) ::
       StructField("randomId",StringType,true) :: Nil)

     val userInfo = sqlContext.createDataFrame(dataRow,schema)
     userInfo.registerTempTable("user_visit_action")
     sqlContext

  }

}
