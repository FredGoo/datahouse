package geex.log.analysis.mapper;

import geex.log.analysis.util.Log;
import geex.log.analysis.util.TextUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlMapper extends Mapper<Object, Text, Text, Text> {
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

        // 获取文件名
        InputSplit inputSplit = context.getInputSplit();
        String fileName = ((FileSplit) inputSplit).getPath().getName();
        logger.info("处理文件名: " + fileName);
        // 获取时间
        Pattern pattern = Pattern.compile("201[4-9][0-1]\\d[0-1]\\d");
        Matcher matcher = pattern.matcher(fileName);
        String date;
        if (matcher.find()) {
            date = matcher.group();
            logger.info("数据时间: " + date);
        } else {
            return;
        }

        // 处理bizAppCommon insert
        if (value.toString().indexOf("INSERT INTO `BIZ_APP_COMMON`") == 0) {
            logger.debug(value);
            String insertVal = value.toString();

            // 获取表名
            String table = "bizAppCommon";
            Text tableOutput = new Text(table);
            logger.info(table);

            // 处理数据
            String[] tablesValues = insertVal.substring(insertVal.indexOf("` VALUES") + 10, insertVal.length() - 2).split("\\),\\(");
            for (String tableValue : tablesValues) {
                Text tableValueOutput = new Text(date + "#" + tableValue);
                logger.debug(tableValue);
                try {
                    context.write(tableOutput, tableValueOutput);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 过滤建表语句
            String tableSchema = "tableSchema";
            Text tableSchemaOutput = new Text(tableSchema);

            // 获取字段
            String tableSchemaValue = fileName + "#" + date + "#" + key + "#" + value;
            logger.debug("表结构: " + tableSchemaValue);
            Text tableSchemaValueOutput = new Text(tableSchemaValue);

            try {
                context.write(tableSchemaOutput, tableSchemaValueOutput);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
