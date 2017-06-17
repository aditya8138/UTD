package assignment1b.part1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class ModifiedWordCount extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.addResource("configuration-1.xml");

        Job job = Job.getInstance(conf, "wordcount");
        job.setJarByClass(this.getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(ModifiedWordCountMapper.class);
        job.setCombinerClass(ModifiedWordCountReducer.class);
        job.setReducerClass(ModifiedWordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        if (job.waitForCompletion(args.length == 3 && args[2].equalsIgnoreCase("verbose"))) {
            System.out.println("Job's Done.");
            return 0;
        }
        System.out.println("Job's Failed, please retry with 'verbose' option to see detail.");
        return 1;
    }

    public static void main(String[] args) {
        int res = 0;
        try {
            res = ToolRunner.run(new ModifiedWordCount(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(res);
    }

    private static class ModifiedWordCountMapper
            extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private final static Text pos = new Text("Total count of positive words:");
        private final static Text neg = new Text("Total count of negative words:");
        private final static HashSet<String> negative = getWordSet("negative-words.txt");
        private final static HashSet<String> positive = getWordSet("positive-words.txt");
        private boolean caseSensitive = false;
        private final Pattern WORD_BOUNDARY = Pattern.compile("\\s*\\b\\s*");

        protected void setup(Mapper.Context context)
                throws IOException,
                InterruptedException {
            Configuration config = context.getConfiguration();
            this.caseSensitive = config.getBoolean("wordcount.case.sensitive", false);
        }

        public void map(LongWritable offset, Text lineText, Context context)
                throws IOException, InterruptedException {
            String line = lineText.toString();
            if (!caseSensitive) {
                line = line.toLowerCase();
            }
            for (String word : WORD_BOUNDARY.split(line)) {
                if (positive.contains(word))
                    context.write(pos, one);
                else if (negative.contains(word))
                    context.write(neg, one);
            }
        }

        private static HashSet<String> getWordSet(String filename) {
            HashSet<String> words = new HashSet<>();

            /* Load file from resource folder to construct positive/negative word HashSet. */
            InputStream inputStream =
                    ModifiedWordCountMapper.class.getClassLoader()
                            .getResourceAsStream(filename);
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                words.add(line.trim());
            }
            scanner.close();
            return words;
        }
    }

    private static class ModifiedWordCountReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        public void reduce(Text word, Iterable<IntWritable> counts, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable count : counts) {
                sum += count.get();
            }
            context.write(word, new IntWritable(sum));
        }
    }
}
