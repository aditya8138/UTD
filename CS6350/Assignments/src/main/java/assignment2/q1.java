package assignment2;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class q1 {

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String delims = "^";
            String[] businessData = StringUtils.split(value.toString(), delims);

            if (businessData.length == 3)
                if(businessData[1].contains("Palo"))
                    context.write(new Text(businessData[1]), new IntWritable(1));
        }
    }

    public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable> {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values,Context context ) throws IOException, InterruptedException {
            int count = 0;
            for(IntWritable t : values)
                count += t.get();
            context.write(key,new IntWritable(count));
        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();		// get all args
        if (otherArgs.length != 2) {
            System.err.println("Usage: assignment2.q1 <in> <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "CountYelp");
        job.setJarByClass(q1.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setCombinerClass(Reduce.class);

        // set output key type
        job.setOutputKeyClass(Text.class);

        // set output value type
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);


        //set the HDFS path of the input data
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        // set the HDFS path for the output
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        //Wait till job completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}