package com.pagerank.init

/**
  * @note 提供SparkCore计算功能,用户重写handle方法即可
  *  2.0
  */
trait SparkFrame extends SparkBaseFrame {

  /**
   * SparkCore执行方法,用户重写业务逻辑,内部使用spark变量替代SparkSession
   */
  protected def handle(): Unit

  /**
   * 调用该方法启动SparkCore程序
   * @return 结果array数组
   */
  def run(): Unit = {
    handle()
  }
}