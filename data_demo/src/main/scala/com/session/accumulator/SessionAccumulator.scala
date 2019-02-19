package com.session.accumulator


import com.session.constants.Constants
import com.session.utils.{MapUtils, StringUtils}
import org.apache.spark.AccumulatorParam

/**
  * 自定义累加器:
  * 这里进行优化，使用redis存储，基于redis队列实现
  */
object SessionAccumulator extends AccumulatorParam[String]{

  private val INITIAL_VALUE: String = Constants.SESSION_COUNT + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_1s_3s + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_4s_6s + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_7s_9s + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_10s_30s + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_30s_60s + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_1m_3m + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_3m_10m + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_10m_30m + "=0" + Constants.VALUE_SEPARATOR +
    Constants.TIME_PERIOD_30m + "=0" + Constants.VALUE_SEPARATOR +
    Constants.STEP_PERIOD_1_3 + "=0" + Constants.VALUE_SEPARATOR +
    Constants.STEP_PERIOD_4_6 + "=0" + Constants.VALUE_SEPARATOR +
    Constants.STEP_PERIOD_7_9 + "=0" + Constants.VALUE_SEPARATOR +
    Constants.STEP_PERIOD_10_30 + "=0" + Constants.VALUE_SEPARATOR +
    Constants.STEP_PERIOD_30_60 + "=0" + Constants.VALUE_SEPARATOR +
    Constants.STEP_PERIOD_60 + "=0"
  /**
    * 最后会执行这个方法，将r2加到r1上
    * 其中，在自定义累加器中，
    * r1代表自定义字符串InitialValue,
    * r2仅仅代表某个区间的值
    * @param r1
    * @param r2
    * @return
    */
  override def addInPlace(r1: String, r2: String): String = {
    if(r1 == ""){
      r2
    }else{
      if(-1 == MapUtils.getPosition(r2)){
        var newR = r1
        /**
          * r2是和r1相同的集合
          * 需要分割r2
          */
        val strs = r2.split("\\|")
        for(i <- 0 to (strs.length-1)){
          //获取key-value
          val key = strs(i).split("=")(0)
          val value = strs(i).split("=")(1)
          //更新指定值，不用更新到r1中，只需要返回即可
          newR = StringUtils.setValueOfSessionOrPage(newR, key, value)
        }
        newR
      }else{
        //获取指定字段的旧值，并+1
        val newValue = StringUtils.getValueOfSessionOrPage(r1, r2).toInt + 1
        //更新指定值，不用更新到r1中，只需要返回即可
        StringUtils.setValueOfSessionOrPage(r1, r2, newValue.toString())
      }
    }
  }

  /*
   * Init 就是SparkContext.accumulator(Init)参数init。
   * 这里的返回值是累计的起始值。注意哦，他可以不等于init。
   *
   * 如果init=10,zero(Init)=0,那么运算过程如下:
   * v1:=0+step
   * v1:=v1+step
   * ...
   * ...
   * 最后v1:=v1+Init
   **/
  override def zero(initialValue: String): String = {
    INITIAL_VALUE
  }
}
