package com.zyh.support.zkClient

import java.util

import org.I0Itec.zkclient.{IZkDataListener,IZkChildListener, ZkClient, ZkConnection}

/**
  * 存在问题，节点的变化和数据的变化不能被监控到
  */
object ZkClientBase {

  private val connect = "localhost:2181"
  private val sessionTimeOut = 2000

  private lazy val zkClient = new ZkClient(new ZkConnection(connect),sessionTimeOut)

  private def getZkClient : ZkClient = {
    //只监听节点的变化，主要针对三个节点触发:新增子节点，减少子节点，删除节点
    zkClient.subscribeChildChanges("/super",new IZkChildListener{
      override def handleChildChange(s: String, list: util.List[String]): Unit = {
        println(s"parnetPath:${s}+' '+'currentChilds:${list}'")
      }
    })

    //只监听节点数据的变化
    //只监控path的数据变化，子节点数据发生变化不会被监控到
    zkClient.subscribeDataChanges("/super",new IZkDataListener {
      override def handleDataChange(s: String, o: scala.Any): Unit = {
        println(s"变更节点为:${s},变更数据为:${o}")
      }
      override def handleDataDeleted(s: String): Unit = {
        println(s"删除的节点为:${s}")
      }
    })
    zkClient
  }

  def test(): Unit ={
    //创建zk客户端
    val zkClient = getZkClient

    //create 和 delete 方法
//    zkClient.createEphemeral("/idea_ephemeral")   //创建临时节点，会话结束后删除
//    zkClient.createPersistent("/super/idea_persistent",true)  //创建持久节点,true表示如果父节点不存在创建父节点
//    Thread.sleep(10000)
//    zkClient.delete("/super/c1") //该节点下有东西时，会提示删除失败
//    zkClient.deleteRecursive("/super") //递归删除，如果该节点下有子节点，会把子节点也删除

    //设置path和data
//    zkClient.createPersistent("/super","1234") //创建并设置节点的值
//    zkClient.createPersistent("/super/c1","lalala")
//    zkClient.createPersistent("/super/s2","hahaha")
//    //获取全部节点
//    val childs = zkClient.getChildren("/super").toArray
//    childs.foreach(println(_))

    //更新节点数据
    zkClient.writeData("/super","456780")
//    val data = zkClient.readData("/super").toString // 注意这里，需要将取出的结果转化为字符串
//    println(data)

    //判断节点是否存在
//    val stat = zkClient.exists("/super")   //Boolean
//    println(stat)
  }

  def main(args: Array[String]): Unit = {
    test()
  }


}
