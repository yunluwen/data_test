package com.adclick.tools

import com.session.utils.DateUtils
import org.apache.spark.sql.{RowFactory, SQLContext}
import org.apache.spark.sql.types._
import org.apache.spark.streaming.dstream.DStream
import com.adclick.domain.AdProvinceTop3
import com.pagerank.dao.DaoFactory
import org.apache.spark.sql.Row

import scala.collection.mutable.ListBuffer
/**
  * two top
  */
object CalculateProvinceTop3Ad {

  def calculateProvinceTop3Ad(adRealTimeStatDStream: DStream[(String,Long)]): Unit ={
    val rowsDStream = adRealTimeStatDStream.transform(rdd => {
      val mappedRDD = rdd.map(tuple => {
        val keySplited = tuple._1.split("_")
        val date = keySplited(0)
        val province = keySplited(1)
        val adid = keySplited(3).toLong
        val clickCount = tuple._2

        val key = date + "_" + province + "_" + adid
        (key, clickCount)
      })

      val dailyAdClickCountByProvinceRDD = mappedRDD.reduceByKey(_+_)

      val rowsRDD = dailyAdClickCountByProvinceRDD.map(tuple => {
        val keySplited = tuple._1.split("_")
        val datekey = keySplited(0)
        val province = keySplited(1)
        val adid = keySplited(2)
        val clickCount = tuple._2.toString

        val date = DateUtils.formatDate(DateUtils.parseDateKey(datekey))
        RowFactory.create(date, province, adid, clickCount)         //创建一个Row
      })

      val schema = StructType(StructField("date",StringType,true) ::
        StructField("province",StringType,true) ::
        StructField("adid",LongType,true) ::
        StructField("clickCount",LongType,true) :: Nil)

      val sqlContext = new SQLContext(rdd.context)

      val dailyAdClickCountByProvinceDF = sqlContext.createDataFrame(rowsRDD,schema)
      dailyAdClickCountByProvinceDF.registerTempTable("tmp_daily_ad_click_count_by_prov")  //注册临时表
      val provinceTop3AdDF = sqlContext.sql(
        "SELECT "
          + "date,"
          + "province,"
          + "ad_id,"
          + "click_count "
          + "FROM ( "
          + "SELECT "
          + "date,"
          + "province,"
          + "ad_id,"
          + "click_count,"
          + "ROW_NUMBER() OVER(PARTITION BY province ORDER BY click_count DESC) rank "
          + "FROM tmp_daily_ad_click_count_by_prov "
          + ") t "
          + "WHERE rank>=3"
      );

      provinceTop3AdDF.rdd
    })

    rowsDStream.foreachRDD(rdd => {
      rdd.foreachPartition(iterator => {
        var adProvinceTop3s = new ListBuffer[AdProvinceTop3]
        while(iterator.hasNext){
          val row: Row = iterator.next()
          val date = row.getString(0)
          val province = row.getString(1)
          val adid = row.getLong(2)
          val clickCount = row.getLong(3)

          val adProvinceTop3 = AdProvinceTop3(date,province,adid,clickCount)
          adProvinceTop3s.append(adProvinceTop3)
        }

        val adClickDao = DaoFactory.apply("AdClick")
        adClickDao.insert()                            //未实现
      })
    })
  }

}
