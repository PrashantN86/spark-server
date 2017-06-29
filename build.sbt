name := "spark-akka-http"

version := "1.0"

scalaVersion := "2.11.8"

val sparkVersion = "2.0.0"

organization := "com.sparkServer"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "com.typesafe.akka" % "akka-http_2.11" % "10.0.3",
  "mysql" % "mysql-connector-java" % "5.1.35"
)
