package geex.log.analysis.reducer;

import geex.log.analysis.util.Log;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class SqlReducer extends Reducer<Text, Text, Text, Text> {
    private static final Log logger = new Log();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        logger.info("### reduce ###");

        if ("tableSchema".equals(key.toString())) {
            // 循环结构
            for (Text value : values) {
                logger.info(value);
            }
        } else {
            // 循环数据
            for (Text value : values) {
                context.write(key, value);
            }
        }
    }
}
