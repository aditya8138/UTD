package assignment3

import org.apache.spark.{SparkConf, SparkContext}

object Q4 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("q1").setMaster("yarn-cluster")
    val sc = new SparkContext(conf)

    val reviewData = sc.textFile("hdfs://cshadoop1/yelp/review/review.csv")
      .map(line => line.split("\\^"))
      .map(line => (line(1), 1))
      .reduceByKey(_+_)
      .sortBy(-_._2)

    val userData = sc.textFile("hdfs://cshadoop1/yelp/user/user.csv")
      .map(line => line.split("\\^"))
      .map(line => (line(0), line(1)))

    val result = userData.join(sc.parallelize(reviewData.take(10)))
      .coalesce(1, shuffle = true)
      .sortBy(-_._2._2)
    result.saveAsTextFile(args(0))
  }
}