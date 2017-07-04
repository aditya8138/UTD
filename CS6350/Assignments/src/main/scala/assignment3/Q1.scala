package assignment3

import org.apache.spark.{SparkConf, SparkContext}

object Q1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("q1").setMaster("yarn-cluster")
    val sc = new SparkContext(conf)

    val reviewData = sc.textFile("hdfs://cshadoop1/yelp/review/review.csv")
      .map(line => line.split("\\^"))
      .map(line => (line(2), (line(3).toDouble, 1)))
      .reduceByKey((a,b) => (a._1+b._1, a._2+b._2))
      .map(line => (line._1, line._2._1/line._2._2))
      .sortBy(-_._2)

    val businessData = sc.textFile("hdfs://cshadoop1/yelp/business/business.csv")
      .map(line => line.split("\\^"))
      .filter(line => line.length == 3)
//      .map(line => (line(0), line(1), line(2)))

    // Directly use join method.
    val result = businessData.map(line => (line(0), (line(1), line(2))))
      .join(sc.parallelize(reviewData.take(10)))
      .coalesce(1, shuffle = true)
    result.saveAsTextFile(args(0)+"join")

    // Hand written reduce side join.
    val newReview = sc.parallelize(reviewData.take(10)).map(line => (line._1, List(line._2.toString)))
    val newBusiness = businessData.map(line => (line(0), List(line(1),line(2))))
    val total = newReview.union(newBusiness)
      .reduceByKey((a,b) => if (a.length == 2) a:::b else b:::a)
      .filter(line => line._2.length == 3)
      .coalesce(1, shuffle = true)
    total.saveAsTextFile(args(0))
  }
}