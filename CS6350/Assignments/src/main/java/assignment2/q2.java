package assignment2;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.HashSet;

public class q2 {

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {

        static HashSet<String> id = new HashSet<>();
        static String dstState;
        static String dstCategory;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            Configuration config = context.getConfiguration();
            Map.dstState = config.getTrimmed("q2.state", "NY");
            Map.dstCategory = config.getTrimmed("q2.category", "Restaurants");
        }

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String delims = "^";
            String[] businessData = StringUtils.split(value.toString(), delims);

            if (businessData.length == 3)
                if(!Map.id.contains(businessData[0]) && businessData[1].contains(Map.dstState) &&
                        businessData[2].contains(Map.dstCategory)) {
                    context.write(new Text(businessData[0]), new Text(businessData[1]));
                    Map.id.add(businessData[0]);
                }
        }
    }

    public static class Reduce extends Reducer<Text,Text,Text,Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values,Context context ) throws IOException, InterruptedException {
            // There are duplicate data, one key might have more than one values,
            // but values are the same. So original code might out duplicate items.
            // for(Text t : values)
            //     context.write(key, t);
            // Consider one ID only have one address, directly output first address.
            context.write(key, values.iterator().next());
        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.addResource("configuration/configuration-2.xml");

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();		// get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: assignment2.q2 <in> <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "GrepYelp");
        job.setJarByClass(q2.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        // No combiner needed.

        // set output key type
        job.setOutputKeyClass(Text.class);

        // set output value type
        job.setMapOutputValueClass(Text.class);
        job.setOutputValueClass(Text.class);


        //set the HDFS path of the input data
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        // set the HDFS path for the output
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        //Wait till job completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}