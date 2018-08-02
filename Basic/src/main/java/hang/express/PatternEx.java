package hang.express;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhangHang
 * @create 2018-04-27 15:08
 **/

/**
 * pattern 对象是一个正则表达式的编译表示。Pattern 类没有公共构造方法。
 * 要创建一个 Pattern 对象，你必须首先调用其公共静态编译方法，它返回一个 Pattern 对象。该方法接受一个正则表达式作为它的第一个参数。
 */
public class PatternEx {
    @Test
    public void pattern() {
        String content = "I am noob " +
                "from runoob.com.";

        String pattern = ".*runoob.*";
        // 创建Pattern对象
        Pattern r = Pattern.compile(pattern);

        Matcher matcher = r.matcher(content);
        boolean isMatch = matcher.find();
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }
}
