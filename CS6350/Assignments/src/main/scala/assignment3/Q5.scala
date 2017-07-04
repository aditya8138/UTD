package assignment3

import org.apache.spark.{SparkConf, SparkContext}

object Q5 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("q1").setMaster("yarn-cluster")
    val sc = new SparkContext(conf)

    val reviewData = sc.textFile("hdfs://cshadoop1/yelp/review/review.csv")
      .map(line => line.split("\\^"))
      .map(line => (line(2), 1))
      .reduceByKey(_+_)
      .sortBy(-_._2)

    val businessData = sc.textFile("hdfs://cshadoop1/yelp/business/business.csv")
      .map(line => line.split("\\^"))
      .filter(line => line(1).contains(args(0)))
      .map(line => (line(0), line(1)))

    val result = businessData.join(reviewData)
      .coalesce(1, shuffle = true)
      .sortBy(-_._2._2)
    result.saveAsTextFile(args(1))
  }
}