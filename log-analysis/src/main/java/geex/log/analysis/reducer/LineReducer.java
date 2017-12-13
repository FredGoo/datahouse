package geex.log.analysis.reducer;

import geex.log.analysis.util.Log;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class LineReducer extends Reducer<Text, Text, Text, Text> {
    private static final Log logger = new Log();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        logger.debug(key);

        Iterator valuesIterator = values.iterator();
        while (valuesIterator.hasNext()) {
            Text value = (Text) valuesIterator.next();
            context.write(key, value);
        }
    }
}
