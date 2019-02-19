package com.pagerank.utils

import com.alibaba.fastjson.JSONObject
import com.session.constants.Constants
import com.session.utils.ParamUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext

/**
  * 操作工具类
  */
object SparkUtils {

  /**
    * 查询指定日期范围内的行为数据
    *
    * @param sQLContext
    */
  def getActionRDDByDateRange(sQLContext: SQLContext,taskParam: JSONObject): RDD[Row] ={
    val startDate = ParamUtils.getParam(taskParam,Constants.PARAM_START_DATE)
    val endDate = ParamUtils.getParam(taskParam,Constants.PARAM_END_DATE)

    val sql = "select * from user_visit_action where date between " + startDate + " and " + endDate;
    val actionDF = sQLContext.sql(sql)
    actionDF.rdd
  }

}

