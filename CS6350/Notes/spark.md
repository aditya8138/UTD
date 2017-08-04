# Spark

1. Spark Core
    - iterative and interactive algorithms. (in Hadoop, they required
      writing.reading from disk/HDFS)
    - in-memory computing
    - lineage preservation
    - RDD
        - blocks of memory
        - transformation & action
        - on-demand action
    - Immutable memory sharing
        - RDD can only be transformed to other RDD
## Spark Libraries
### Spark SQL

1. Datafram enhanced API for tabular data
2. Dataset enhancement for typed data

### MLlib machine learning & analytics

    to do

### GraphX - specialized API for graph data.

    to do

### Spark Streaming

#### Objective
A framework for big data stream processing that
- Scales Fraud to hundreds detection of nodes
- Achieves second-scale latencies
- Efficiently recover from failures
- Integrates with batch and interactive processing

#### Stateful Stream Processing

Traditional model

- Processing pipeline of nodes
- Each node maintains mutable state
- Each input record update the state and new records are sent out.

Mutable state is lost if node fails.

#### Spark Streaming Model
Run a streaming computation as a series of very small, deterministic batch
jobs.

- Chop up the live stream into batches of X seconds.
- Spark treats each batch of data as RDDs and processes them using RDD
  operations.
- Finally, the processed results of the RDD operations are returned in batches.
- Batch sizes as low as ½ second, latency of about 1 second.
- Potential for combining batch processing and streaming processing in the same
  system.

An example:
```scala
val tweets = ssc.twitterStream() // ssc is streaming Spark context
val hashTags = tweets.flatMap(status => getTags(status))
hashTags.saveAsHadoopFiles("hdfs://...")
```
```java
JavaDStream<Status> tweets = ssc.twitterStream()
JavaDstream<String> hashTags = tweets.flatMap(new Function<...> { })
hashTags.saveAsHadoopFiles("hdfs://...")
```

#### Summary

- It provides micro second latency with fault tolerance.
- Divides streaming data into micro baches (DStreams)
- Batches are used to create RDD and fed into Spark core engine.
- DStream are RDD for 1/2 second intervals
- Spark core takes care of fault tolerance
- SSC - to take real time data and divide into batches
- Native support for various types of data, e.g. Kafka, HDFS, Flume, Akka
  Actors, Raw TCP sockets, etc.
