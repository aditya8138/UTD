package assignment2;

import hadoop.definitive.guide.FloatTupleWritable;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.*;

public class q4 {

    public static class Map extends Mapper<LongWritable, Text, Text,FloatTupleWritable> {

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String delims = "^";
            String[] businessData = StringUtils.split(value.toString(), delims);

            if (businessData.length == 4) {
                context.write(new Text(businessData[2]), new FloatTupleWritable(
                        new FloatWritable(1),
                        new FloatWritable(Float.valueOf(businessData[3]))));
            }
        }
    }

    public static class Reduce extends Reducer<Text,FloatTupleWritable,Text,FloatWritable> {
        static private java.util.Map<Text, FloatWritable> countMap = new HashMap<>();
        static int topn = 0;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            Configuration config = context.getConfiguration();
            Reduce.topn = config.getInt("q4.topn", 10);
        }

        @Override
        public void reduce(Text key, Iterable<FloatTupleWritable> values,Context context)
                throws IOException, InterruptedException {
            int count = 0;
            float total = 0;
            for(FloatTupleWritable t : values) {
                count += t.getFst().get();
                total += t.getSnd().get();
            }
            Reduce.countMap.put(new Text(key), new FloatWritable(total / count));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            java.util.Map<Text, FloatWritable> sortedMap = sortByValues(Reduce.countMap);
            int counter = 0;
            for (Text key : sortedMap.keySet()) {
                if (counter++ == Reduce.topn)
                    break;
                context.write(key, sortedMap.get(key));
            }
        }
    }

    /**
     * The combiner retrieves every word and puts it into a Map: if the word already exists in the
     * map, increments its value, otherwise sets it to 1.
     */
    public static class Combiner extends Reducer<Text,FloatTupleWritable,Text,FloatTupleWritable> {
        @Override
        public void reduce(Text key, Iterable<FloatTupleWritable> values,Context context)
                throws IOException, InterruptedException {
            int count = 0;
            float total = 0;
            for(FloatTupleWritable t : values) {
                count += t.getFst().get();
                total += t.getSnd().get();
            }
            context.write(new Text(key), new FloatTupleWritable(
                    new FloatWritable(count), new FloatWritable(total)));
        }
    }


    /*
    * sorts the map by values. Taken from:
    * http://javarevisited.blogspot.it/2012/12/how-to-sort-hashmap-java-by-key-and-value.html
    */
    private static <K extends Comparable, V extends Comparable> java.util.Map<K, V> sortByValues(java.util.Map<K, V> map) {
        List<java.util.Map.Entry<K, V>> entries = new LinkedList<>(map.entrySet());

        Collections.sort(entries, new Comparator<java.util.Map.Entry<K, V>>() {

            @Override
            public int compare(java.util.Map.Entry<K, V> o1, java.util.Map.Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        java.util.Map<K, V> sortedMap = new LinkedHashMap<>();

        for (java.util.Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.addResource("configuration/configuration-2.xml");

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();		// get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: assignment2.q3 <in> <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "ZipCodeCount");
        job.setJarByClass(q4.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setCombinerClass(Combiner.class);

        // set output key type
        job.setOutputKeyClass(Text.class);

        // set output value type
        job.setMapOutputValueClass(FloatTupleWritable.class);
        job.setOutputValueClass(Text.class);


        //set the HDFS path of the input data
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        // set the HDFS path for the output
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        //Wait till job completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}