package com.zyh.support.zookeeper

import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooKeeper}
/**
  * zookeeper测试
  */
object SimpleZkClient {

  //注意这里，为什么不能把属性设置为private，设置成private之后，方法调用不起作用
  val connect = "localhost:2181"
  val sessionTimeOut = 2000

  lazy val zkClient = new ZooKeeper(connect,sessionTimeOut,new Watcher {
    override def process(watchedEvent: WatchedEvent): Unit = {
      println(watchedEvent.getType+"---"+watchedEvent.getPath)
    }
  })

  /**
    * 数据的增删改查
    */
  def createZNode(): Unit ={
    //测试创建节点
    //节点路径，节点数据(上传的数据都要转换为byte数组)，节点权限，节点类型(持久节点:PERSISTENT，临时节点:EPHEMERAL)
    zkClient.create("/idea","hello".getBytes,Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT)
  }

  //获取子节点
  def getZNode(): Unit ={
    //节点路径，是否使用zkClient的Watch监听器
    val childrens = zkClient.getChildren("/",true)
    println(childrens)
  }

  //判断节点是否存在
  def existZNode(): Unit ={
    val stat = zkClient.exists("/idea",false)
    println(stat!=null)
  }

  //获取节点下面的数据
  def getData(): Unit ={
    val data = zkClient.getData("/idea",false,null)
    println(new String(data,"utf-8"))
  }

  //删除ZNode
  def deleteZNode(): Unit ={
    //参数2指定要删除的版本，-1代表删除全部版本
    zkClient.delete("/idea",-1)
  }

  //修改ZNode的Data
  def setZNode(): Unit ={
    zkClient.setData("/idea","love".getBytes(),-1)
  }


  def main(args: Array[String]): Unit = {
//    createZNode()
//    getZNode()
//    existZNode()
    getData()
    setZNode()
    getData()
//    deleteZNode()
  }
}
