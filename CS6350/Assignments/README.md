# Assignments Solutions for CS6350 2017 Summer Big Data
The solutions for each assignment are packaged as `cs6350.assignmentX.partY`.

*All the solutions are not graded.*

## Compilation

To compile, simply using maven command `mvn package`, a package named *CS6350Assignment-1.0-SNAPSHOT.jar* would be generated in `target` folder.

## Execution
All execution need to be performed on department machine with Hadoop configured.

### Assignment 1
- Part 1

    ```bash
    # Delete if directory exist.
    hdfs dfs -rm -r assignment1/part1

    # Generate links configuration file.
    echo "http://www.utdallas.edu/~axn112530/cs6350/lab2/input/20417.txt.bz2
    http://www.utdallas.edu/~axn112530/cs6350/lab2/input/5000-8.txt.bz2
    http://www.utdallas.edu/~axn112530/cs6350/lab2/input/132.txt.bz2
    http://www.utdallas.edu/~axn112530/cs6350/lab2/input/1661-8.txt.bz2
    http://www.utdallas.edu/~axn112530/cs6350/lab2/input/972.txt.bz2
    http://www.utdallas.edu/~axn112530/cs6350/lab2/input/19699.txt.bz2" > link.txt

    # Download files from link.txt and upload to HDFS and decompress afterward.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment1.part1.DownloadAndDecompress \
        link.txt hdfs://cshadoop1/user/netID/assignment1/part1/

    # Display result
    hdfs dfs -ls assignment1/part1
    ```

- Part 2
    ```bash
    # Delete if directory exist.
    hdfs dfs -rm -r assignment1/part2

    # Download Wikipedia (1.8 billion)
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment1.part2.DownloadAndDecompressArchive \
        http://corpus.byu.edu/wikitext-samples/text.zip \
        hdfs://cshadoop1/user/netID/assignment1/part2/wiki/

    # Download GloWbE (1.8 billion)
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment1.part2.DownloadAndDecompressArchive \
        http://corpus.byu.edu/cohatext/samples/text.zip \
        hdfs://cshadoop1/user/netID/assignment1/part2/cohatext

    # Display result
    hdfs dfs -ls assignment1/part2
    ```

### Assignment 1b
- Part 1

    ```bash
    # Delete if directory exist.
    hdfs dfs -rm -r assignment1b/part1/

    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment1b.part1.ModifiedWordCount \
        hdfs://cshadoop1/user/netID/assignment1/part1 \
        hdfs://cshadoop1/user/netID/assignment1b/part1

    # Display result
    hdfs dfs -cat assignment1b/part1/part-r-00000
    ```
- Part 2
    ```bash
    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment1b.part2.POSCount \
        hdfs://cshadoop1/user/hxh160630/assignment1/part2/wiki \
        hdfs://cshadoop1/user/hxh160630/assignment1b/part2

    # The result would print on screen automatically, and store in the
    # following path with name result.txt
    hdfs dfs -cat hdfs://cshadoop1/user/netID/assignment1b/part2/result
	```

### Assignment 2
- Q1
    ```bash
    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment2.q1 /yelp/business/business.csv assignment2/1

    # Display result
    hdfs dfs -cat assignment2/1/part-r-00000
    ```
- Q2
    ```bash
    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment2.q2 /yelp/business/business.csv assignment2/2

    # Display result
    hdfs dfs -cat assignment2/2/part-r-00000
    ```
- Q3
    ```bash
    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment2.q3 /yelp/business/business.csv assignment2/3

    # Display result
    hdfs dfs -cat assignment2/3/part-r-00000
    ```
- Q4
    ```bash
    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment2.q4 /yelp/review/review.csv assignment2/4

    # Display result
    hdfs dfs -cat assignment2/4/part-r-00000
    ```
- Q5
    ```bash
    # Process files in specified folder.
    hadoop jar CS6350Assignment-1.0-SNAPSHOT.jar \
        assignment2.q5 /yelp/review/review.csv assignment2/5

    # Display result
    hdfs dfs -cat assignment2/5/part-r-00000
    ```
## Some Remarks
### Assignment 1b
#### Load Dependency File
A challenge in this assignment is to generate the HashMap/HashSet needed when
filtering word in Mapper.
The files to generate HashMap/HashSet can be downloaded from Internet. In other
word, these dependency file is part of the program.

Since the generation of the HashMap/HashSet is during Mapper class, if the file
is store on one local machine, the actual JVM running the Mapper class may not
have access to that particular location.
Thus, we came up with two kinds of solution to load the file during runtime.
- __Upload the file to HDFS first,__ since file in HDFS can be accessed by all
  JVM that runs Mapper. In particular, there are two ways to load file in HDFS.
    1. Use distributed cache. However, the cluster of UTD CS department did not
       allow distributed cache.
    1. Use `FileSystem` class to open an `InputStream` of the file, and read
       input stream.
- __Package the file inside jar,__ and load the resource file from the resource
  folder.

The first solution requires user to upload files to HDFS before execution and
must provide the location of the dependency file, which makes the solution not
friendly to use. Thus, the second solution was adapted.

Nevertheless, the second solution has its subtle point too.

In general, Java provide several methods in classes `Class` and `ClassLoader`
to locate resources. More reference see [Location-Independent Access to 
Resources](https://docs.oracle.com/javase/8/docs/technotes/guides/lang/resources.html)
In particular, to read text file in resource folder, we can use
`getResourceAsStream` method.

In our MapReduce situation, Mapper class is statically defined, so is the
HashSet instance. So the resource file need to be statically loaded. Here is
the code segment in class `ModifiedWordCountMapper`.
```java
private static HashSet<String> getWordSet(String filename) {
    HashSet<String> words = new HashSet<>();

    /* Statically load file from resource folder to construct positive/negative word HashSet. */
    InputStream inputStream = ModifiedWordCountMapper.class.getClassLoader()
            .getResourceAsStream(filename);

    Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
    while (scanner.hasNextLine())
        words.add(scanner.nextLine().trim());
    scanner.close();
    return words;
}
```
To load resource normally (dynamically), we can directly use `getClass()`
method to get current class.
[[Ref]](https://www.mkyong.com/java/java-getresourceasstream-in-static-method/)
```java
InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
```

#### Mapper and Reducer Design of Part 2
The previous solution works just fine. However, it does not have a combiner
class, hence might be slow in some cases. If we are to reuse the reducer
class for the combiner directly, the reducer must output the same
`<key,value>` pair as the mapper does.

To fix this, we can move the array, which is used to count the occurrence of
each POS, from reducer to mapper. Each time encountered a POS, the map write an
vector of all `0` and a `1` indicating that POS. Then in reducer, we simply
need to implement a vector plus function to sum up all the receiving vector.
The `<key,value>` pair would be `<Length of Word, Vector of POS Count>`

The coding is left To-do.
