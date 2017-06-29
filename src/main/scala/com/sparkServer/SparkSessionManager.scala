package com.sparkServer

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * Created by prashant on 19/6/17.
 */

class SparkSessionManager private () {

  private val sparkConf = new SparkConf()
    .setMaster("local")
    .setAppName("spark-server")
    .set("spark.cores.max", "2")
    .set("spark.executor.memory", "4g")
    .setJars(Array("/home/prashant/.ivy2/cache/mysql/mysql-connector-java/jars/mysql-connector-java-5.1.35.jar"));

  private val spark = SparkSession
    .builder.config(sparkConf)
    .getOrCreate()

  def getSparkSession: SparkSession = spark
}

object SparkSessionManager {
  private val _instance = new SparkSessionManager().getSparkSession
  def sparkSessionInstance: SparkSession = _instance
}
