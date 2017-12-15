package geex.log.analysis;

import geex.log.analysis.config.FileConfig;
import geex.log.analysis.mapper.SqlMapper;
import geex.log.analysis.reducer.SqlReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Application {
    public static void main(String args[]) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "log analysis");

        job.setMapperClass(SqlMapper.class);
//        job.setCombinerClass(SqlReducer.class);
        job.setReducerClass(SqlReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(FileConfig.OUTPUT + "\\SQL_20161201-023001\\bizAppCommon2016120101"));
        FileOutputFormat.setOutputPath(job, new Path("log-analysis/output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}