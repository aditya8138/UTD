package assignment3

import org.apache.spark.{SparkConf, SparkContext}

object Q2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("q1").setMaster("yarn-cluster")
    val sc = new SparkContext(conf)

    val userData = sc.textFile("hdfs://cshadoop1/yelp/user/user.csv")
      .map(line => line.split("\\^"))
      .filter(line => line(1).equals(args(0)))
      .map(line => (line(0), line(1)))

    val reviewData = sc.textFile("hdfs://cshadoop1/yelp/review/review.csv")
      .map(line => line.split("\\^"))
      .map(line => (line(1), (line(3).toDouble, 1)))
      .reduceByKey((a,b) => (a._1+b._1, a._2+b._2))
      .map(line => (line._1, line._2._1/line._2._2))

    val result = userData.join(reviewData).coalesce(1, shuffle = true)
    result.saveAsTextFile(args(1))
  }
}