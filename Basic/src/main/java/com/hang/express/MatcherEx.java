package com.hang.express;

/**
 * @author ZhangHang
 * @create 2018-04-27 15:10
 **/

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Matcher 对象是对输入字符串进行解释和匹配操作的引擎。
 * 与Pattern 类一样，Matcher 也没有公共构造方法。你需要调用 Pattern 对象的 matcher 方法来获得一个 Matcher 对象。
 */

public class MatcherEx {

    @Test
    public void matcher(){
        String content = "I am noob " +
                "from runoob.com.";

        String pattern = ".*runoob.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }
}
