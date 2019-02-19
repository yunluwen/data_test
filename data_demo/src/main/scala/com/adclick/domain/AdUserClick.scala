package com.adclick.domain

/**
  * pro user domain
  */

case class AdUserClickCount(date:String,userid:Long,adid:Long,clickCount:Long)

case class AdBlack(userId: Long)

case class AdStat(date: String,province: String,city: String,adid: Long,clickCount: Long)

case class AdProvinceTop3(date: String,province: String,adid: Long,clickCount: Long)

case class AdClickTrend(date: String,hour: String,minute: String,adid: Long,clickCount: Long)