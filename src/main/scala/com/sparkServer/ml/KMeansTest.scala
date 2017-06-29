package com.sparkServer.ml

import org.apache.spark.ml.clustering.{ KMeans, KMeansModel }
import org.apache.spark.ml.feature.{ Normalizer, StringIndexer, VectorAssembler }
import org.apache.spark.ml.{ Pipeline, PipelineModel }
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DoubleType

/**
 * Created by prashant on 19/6/17.
 */
object KMeansTest {

  def runKmeans(sparkSession: SparkSession): String = {

    val prop = new java.util.Properties
    prop.setProperty("user", "root")
    prop.setProperty("password", "root")
    prop.setProperty("driver", "com.mysql.jdbc.Driver")

    val rawdf = sparkSession.read.jdbc("jdbc:mysql://192.168.0.17:3306/titanic", "TRAIN", prop)
      .na.drop()

    val df = rawdf.withColumn("label", rawdf("SURVIVED").cast(DoubleType))

    df.printSchema()

    val EmbarkedIndexer = new StringIndexer()
      .setInputCol("EMBARKED")
      .setOutputCol("EMBARKEDI")

    val GenderIndexer = new StringIndexer()
      .setInputCol("SEX")
      .setOutputCol("GENDER")

    val featureList = List("AGE", "PCLASS", "FARE", "SIBSP", "PARCH", "EMBARKEDI", "GENDER")

    val assembler = new VectorAssembler()
      .setInputCols(featureList.toArray)
      .setOutputCol("features")

    val normalizer = new Normalizer().setInputCol("features").setOutputCol("normalisedFeatures")

    val km = new KMeans().setK(2).setSeed(1L).setFeaturesCol("normalisedFeatures")

    val FeatureTransformationPipeline = new Pipeline()
      .setStages(Array(EmbarkedIndexer, GenderIndexer, assembler, normalizer))

    val pmodel: PipelineModel = FeatureTransformationPipeline.fit(df)

    val nTrainDf = pmodel.transform(df)

    val kmModel: KMeansModel = km.fit(nTrainDf)

    kmModel.save("models/kmModel")

    val WSSSE = kmModel.computeCost(nTrainDf)

    WSSSE.toString
  }

}
