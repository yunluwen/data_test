package com.session.main

import com.alibaba.fastjson.JSON
import com.session.caterorysort.ClickCaterorySort
import com.session.init.Init
import com.session.userquery.UserQuery
import com.session.utils.ParamUtils
import com.session.dao.TaskDaoFactory
import com.session.sessionpoly.SessionPoly
import com.session.constants.Constants
import com.session.step.Steps;

object App {

  def main(args: Array[String]): Unit = {
    val sc = Init.initSparkContext()
    val sessionpoly = SessionPoly.sessionPoly(sc._1,args(1))
    val sQLContext = sc._2

    /*
    * 创建Dao组件，用来操作数据库
    * */
    val taskDao =  TaskDaoFactory.getTaskDao()
    val taskId = ParamUtils.getTaskIdFromArgs(args(0),Constants.SPARK_LOCAL_SESSION_TASKID).longValue()
    val task = if (taskId > 0) taskDao.getTask(taskId) else null
    if (task == null) println("Can't find task by id: " + taskId)

    val taskParam = JSON.parseObject(task.getTaskParam())

    /*用户查询结果*/
    val resultforearch = UserQuery.userQuery(sc._1,args(1),taskParam.getString("searchWords"),
      taskParam.getString("clickCaterory"),taskParam.getString("ages"),taskParam.getString("professional"), taskParam.getString("city"))
    val stepAndperiod = Steps.steps(sc._1)
    /*排序*/
    val resultforsort = ClickCaterorySort.clickCaterorySort(sc._1,args(1),taskParam.getString("searchWords"), taskParam.getString("clickCaterory"),
      taskParam.getString("ages"),taskParam.getString("professional"), taskParam.getString("city"))

    println(resultforearch)
    println(stepAndperiod)
    println(resultforsort)



  }
}