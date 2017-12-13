package geex.log.analysis.util;

public class TextUtil {
    public static Boolean isMySqlAnnotation(String content) {
        return content.indexOf("--") == 0 || content.indexOf("/*!") == 0;
    }
}
