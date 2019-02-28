package com.zyh.house_scala.web.dto

import scala.beans.BeanProperty
import scala.language.implicitConversions

final class QiNiuPutRet {

  @BeanProperty
  val key:String = null
  @BeanProperty
  val hash:String = null
  @BeanProperty
  val bucket:String = null
  @BeanProperty
  val width:String = null
  @BeanProperty
  val height:String = null

  override def toString = s"QiNiuPutRet($key, $hash, $bucket, $width, $height)"
}
