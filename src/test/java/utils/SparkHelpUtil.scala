package com.wxj.utils

import com.wxj.advertisement.UDF.WXJUdf
import org.apache.spark.SparkConf
import org.apache.spark.sql._
//import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ArrayBuffer

object SparkHelpUtil {

 // private val logger: Logger = LoggerFactory.getLogger(SparkHelpUtil.getClass)

  def readTableDate(sparkSession: SparkSession, tableName: String, column: ArrayBuffer[String]) = {
    import  sparkSession.implicits._
    val tableDF = sparkSession.read.table(tableName)
      .selectExpr(column:_*) //这种select方法 可以自动把数组里的数据拆分
    tableDF
  }
  def writeTableDate(sourceDF: DataFrame, tableName: String, saveMode: SaveMode): Unit = {
   // sourceDF.write.saveAsTable(tableName)
    sourceDF.write.mode(saveMode).insertInto(tableName)
  }
  def createSpark(conf: SparkConf) = {
    val sparkSession = SparkSession.builder().config(conf)
      .enableHiveSupport()
      .getOrCreate()
  //加载自定义函数
    registerFun(sparkSession)

    sparkSession
  }

  /**
    * UDF注册
    */
  def registerFun(spark:SparkSession):Unit={
    //处理年龄段
    spark.udf.register("getAgeRange",WXJUdf.getAgeRange _)

    //获得action对应的分数
    spark.udf.register("getScore",WXJUdf.getScore _)

  }


  /**
    * 参数校验
    * @param bdp_day_begin
    * @param bdp_day_end
    * @return
    */

  def rangeDate(bdp_day_begin: String, bdp_day_end: String) = {
      val bdp_days = new ArrayBuffer[String]()

    try{
      val begin = DateUtils.dateFromat4String(bdp_day_begin,"yyyy-MM-dd")
      val end =  DateUtils.dateFromat4String(bdp_day_end,"yyyy-MM-dd")

      if(begin.equals(end)){
        bdp_days .+= (begin)
      }else{
        var tempday = begin
        while(tempday != end){
          bdp_days .+= (tempday)
          tempday = DateUtils.dateFromat4StringDiff(tempday,1,"yyyy-MM-dd")
        }
      }
    }catch {
      case e:Exception=>{
        println(e.getMessage)
      }
    }
      bdp_days
  }
}
