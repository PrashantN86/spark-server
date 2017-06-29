package com.sparkServer

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.sparkServer.ml.KMeansTest

trait SparkServices {

  val sparkSession = SparkSessionManager.sparkSessionInstance
  val route =
    path("version") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Spark Version " + sparkSession.version + "</h1>"))
      }
    } ~
      path("kmeans") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>WSSE " + KMeansTest.runKmeans(sparkSession) + "</h1>"))
        }
      }
}
