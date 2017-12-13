package geex.log.analysis.mapper;

import geex.log.analysis.util.Log;
import geex.log.analysis.util.TextUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LineMapper extends Mapper<Object, Text, Text, Text> {
    private static final Log logger = new Log();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // 过滤注释
        if (TextUtil.isMySqlAnnotation(value.toString())) return;
        // 过滤空
        if (value.getLength() <= 0) return;
        // 过滤drop语句
        if (value.toString().indexOf("DROP") == 0) return;
        // 过滤lock语句
        if (value.toString().indexOf("LOCK") == 0) return;

        // 处理insert
        if (value.toString().indexOf("INSERT") == 0) {
            logger.debug(value);
            String insertVal = value.toString();

            // 获取表名
            String table = insertVal.substring(insertVal.indexOf("INTO `") + 6, insertVal.indexOf("` VALUES"));
            Text tableOutput = new Text(table);
            logger.debug(table);

            // 处理数据
            String[] tablesValues = insertVal.substring(insertVal.indexOf("` VALUES") + 10, insertVal.length() - 2).split("\\),\\(");
            for (String tableValue : tablesValues) {
                Text tableValueOutput = new Text(tableValue);
                logger.debug(tableValue);
                try {
                    context.write(tableOutput, tableValueOutput);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
