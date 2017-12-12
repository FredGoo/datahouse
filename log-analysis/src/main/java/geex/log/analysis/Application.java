package geex.log.analysis;

import geex.log.analysis.config.FileConfig;
import geex.log.analysis.mapper.LineMapper;
import geex.log.analysis.reducer.LineReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Application {
    public static void main(String args[]) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "log analysis");

        job.setMapperClass(LineMapper.class);
        job.setCombinerClass(LineReducer.class);
        job.setReducerClass(LineReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(FileConfig.OUTPUT + FileConfig.TEMP_UNTAR_FILE_NAME));
        FileOutputFormat.setOutputPath(job, new Path("log-analysis/output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}