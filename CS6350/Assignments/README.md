# Assignments Solutions for CS6350 2017 Summer Big Data
The solutiuons for each assignment are packaged as `cs6350.assignmentX.partY`.

*All the solutions are not graded.*

## Compilation

To compile, simply using maven command `mvn package`, a package named *CS6350Assignment-1.0-SNAPSHOT.jar* would be generated in `target` folder.

## Execution
All execution need to be performed on department machine with hadoop configured.

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
	    hdfs://cshadoop1/user/netID/assignment1/part2/cohatext/
	    
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
	    hdfs://cshadoop1/user/netID/assignment1/part2/wiki \
        hdfs://cshadoop1/user/netID/assignment1b/part2
	```