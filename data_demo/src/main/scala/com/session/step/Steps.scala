package com.session.step


import com.session.accumulator.SessionAccumulator
import com.session.constants.Constants
import com.session.utils.DateUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext


object Steps {

  /**
    * param:sc:SparkContext
    */
  def steps(sc:SparkContext): String ={
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //设置累加器
    val myAccumulator = sc.accumulator("")(SessionAccumulator)

    //读取click log 数据，进行session数据统计
    val sessionLines = sc.textFile(Constants.CLICK_LOG_PATH)
    sessionLines.filter(!_.equals("")).map(line => {
      //分割字符，获取每项数据
      val clickData = line.split("\t")
      /**
        * key-value格式设置
        * key:sessionid: String
        * value:starttime=value|endtime=value|step=value
        */
      //首先获取sessionid
      val sessionid = clickData(2)
      val value = "starttime="+clickData(4)+"|endtime="+clickData(4)+"|step=1"
      //返回key-value类型
      (sessionid, value)
    }).reduceByKey((x,y)=>{
      /**
        * 设置相应key-value格式
        * key:即为sessionid
        * value:设置为starttime=value|endtime=value|step=value
        */
      /**
        * 相同sessionid的项目合并
        * 先用"|"分割字符串，再用"="获取值
        */
      //用"|"分割各项目
      val clickdata_x = x.split("\\|")
      val clickdata_y = y.split("\\|")
      /**
        * 获取起始和结束时间
        * 取比较小的时间为起始时间
        * 取比较大的时间为结束时间
        */
      val startime = {
        val start_x = clickdata_x(0).split("=")(1)
        val start_y = clickdata_y(0).split("=")(1)
        if(DateUtils.before(start_x, start_y, Constants.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)){
          start_x
        }else{
          start_y
        }
      }
      val endtime = {
        val end_x = clickdata_x(1).split("=")(1)
        val end_y = clickdata_y(1).split("=")(1)
        if(DateUtils.after(end_x, end_y, Constants.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)){
          end_x
        }else{
          end_y
        }
      }
      /**
        * 已知步长为相同sessionid的个数
        * 因而将x和y的step相加即可
        */
      val step = clickdata_x(2).split("=")(1).toInt + clickdata_y(2).split("=")(1).toInt
      //组成value:starttime=value|endtime=value|step=value
      val value = "starttime="+startime+"|endtime="+endtime+"|step="+step
      value
    }).foreach(line =>{
      /**
        * 先用"|"分割字符
        * 通过将starttime和endtime计算时间差 来 进行累加操作
        */
      val datas = line._2.split("\\|")
      //获取起始时间
      val starttime = datas(0).split("=")(1)
      val endtime = datas(1).split("=")(1)
      //计算时间
      val time = DateUtils.getMinuteOfTwoTime(endtime,starttime,Constants.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
      //获取步长
      val step = datas(2).split("=")(1).toInt
      //根据访问时长，更新累加器
      if(1<=time && 3>=time){
        myAccumulator.add(Constants.TIME_PERIOD_1s_3s);
      }else if(4<=time && 6>=time){
        myAccumulator.add(Constants.TIME_PERIOD_4s_6s);
      }else if(7<=time && 9>=time){
        myAccumulator.add(Constants.TIME_PERIOD_7s_9s);
      }else if(10<=time && 30>=time){
        myAccumulator.add(Constants.TIME_PERIOD_10s_30s);
      }else if(31<=time && 60>=time){
        myAccumulator.add(Constants.TIME_PERIOD_30s_60s);
      } else if (time >= 61 && time <= 180)
        myAccumulator.add(Constants.TIME_PERIOD_1m_3m)
      else if (time >= 181 && time <= 600)
        myAccumulator.add(Constants.TIME_PERIOD_3m_10m)
      else if (time >= 601 && time <= 1800)
        myAccumulator.add(Constants.TIME_PERIOD_10m_30m)
      else
        myAccumulator.add(Constants.TIME_PERIOD_30m)


      //根据步长，更新累加器
      // 根据访问步长，更新累加器
      if (step >= 1 && step <= 3)
        myAccumulator.add(Constants.STEP_PERIOD_1_3)
      else if (step >= 4 && step >= 6)
        myAccumulator.add(Constants.STEP_PERIOD_4_6)
      else if (step >= 7 && step <= 9)
        myAccumulator.add(Constants.STEP_PERIOD_7_9)
      else if (step >= 10 && step<= 30)
        myAccumulator.add(Constants.STEP_PERIOD_10_30)
      else if (step>= 31 && step <= 60)
        myAccumulator.add(Constants.STEP_PERIOD_30_60)
      else if (step >= 61)
        myAccumulator.add(Constants.STEP_PERIOD_60)

    })
    myAccumulator.value
  }
}
