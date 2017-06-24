package assignment1b.part2;

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
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class POSCount extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.addResource("configuration/configuration-1b.xml");

        Job job = Job.getInstance(conf, "poscount");
        job.setJarByClass(this.getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(POSCountMapper.class);
//        job.setCombinerClass(ModifiedWordCountReducer.class);
        job.setReducerClass(POSCountReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
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
            res = ToolRunner.run(new POSCount(), args);

            if (res == 0) {
                ResultPrinter rp = new ResultPrinter(args[1] + "/part-r-00000");
                rp.printResult();
                rp.writeResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(res);
    }

    private static class POSCountMapper
            extends Mapper<LongWritable, Text, IntWritable, Text> {
        private final static HashMap<String, Character> pos = getWordSet("mpos/mobyposi.i");
        private boolean caseSensitive = false;
        private final Pattern WORD_BOUNDARY = Pattern.compile("\\s*\\b\\s*");

        protected void setup(Context context)
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
                if (word.length() < 5)
                    continue;
                if (pos.containsKey(word))
                    context.write(new IntWritable(word.length()), new Text(pos.get(word).toString()));
                else
                    context.write(new IntWritable(word.length()), new Text("n"));
                context.write(new IntWritable(word.length()), new Text("T"));
                if (this.isPalindrome(word))
                    context.write(new IntWritable(word.length()), new Text("x"));
            }
        }

        private static HashMap<String, Character> getWordSet(String filename) {
            HashMap<String, Character> pos = new HashMap<>();

            /* Load file from resource folder to construct POS HashMap. */
            InputStream inputStream =
                    POSCountMapper.class.getClassLoader()
                            .getResourceAsStream(filename);
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int i;
                for (i = line.length()-1; (int)line.charAt(i) != 65533 && i >= 0; i--);
                pos.put(line.substring(0, i), line.charAt(i+1));
            }
            scanner.close();
            return pos;
        }

        private boolean isPalindrome(String word) {
            for (int i = 0, j = word.length() - 1; i < j; ++i, --j)
                if (word.charAt(i) != word.charAt(j))
                    return false;
            return true;
        }
    }

    private static class POSCountReducer
            extends Reducer<IntWritable, Text, IntWritable, Text> {
        private static HashMap<String, Integer> pmap = createMap();

        private static HashMap<String, Integer> createMap() {
            HashMap<String, Integer> myMap = new HashMap<>();
            myMap.put("T", 0);  // Total
            myMap.put("N", 1);  // Noun
            myMap.put("r", 2);  // Pronoun
            myMap.put("V", 3);  // Verb (usu participle)
            myMap.put("t", 4);  // Verb (transitive)
            myMap.put("i", 5);  // Verb (intransitive)
            myMap.put("v", 6);  // Adverb
            myMap.put("A", 7);  // Adjective
            myMap.put("C", 8);  // Conjunction
            myMap.put("P", 9);  // Preposition
            myMap.put("!", 10); // Interjection
            myMap.put("p", 12); // Palindromes
            myMap.put("n", 11); // No Match POS
            return myMap;
        }

        @Override
        public void reduce(IntWritable len, Iterable<Text> pos, Context context)
                throws IOException, InterruptedException {
            int[] sum = { 0,0,0,  0,0,0,  0,0,0,  0,0,0,  0 };

            for (Text p : pos) {
                if (POSCountReducer.pmap.containsKey(p.toString()))
                    sum[POSCountReducer.pmap.get(p.toString())] += 1;
            }
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i <= 12; i++)
                sb.append(sum[i]).append("\t");

            context.write(len, new Text(sb.toString()));
        }
    }
}
