package com.hang.express;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhangHang
 * @create 2018-04-27 15:26
 **/


public class GroupEx {
    @Test
    public void group(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            // group(0)代表整个表达式
            System.out.println("Found value: " + m.group(0) );
            //第一个括号的表达式
            System.out.println("Found value: " + m.group(1) );
            //第二个括号的表达式
            System.out.println("Found value: " + m.group(2) );
            //第三个括号的表达式
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }
}
