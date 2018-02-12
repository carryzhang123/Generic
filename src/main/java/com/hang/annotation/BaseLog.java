package com.hang.annotation;

/**
 * @author ZhangHang
 * @create 2018-01-25 17:44
 **/

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 基础日志
 *
 */
@MyTable(name="T_BaseLog")
public class BaseLog{

    @MyField(name="addTime",type="Date")
    private Date log_time; // 时间

    @MyField(name="log_level",type="String")
    private String log_level; // 级别

    @MyField(name="message",type="String")
    private String message; // 日志内容

    public Date getLog_time()
    {
        return log_time;
    }

    public void setLog_time(Date log_time)
    {
        this.log_time = log_time;
    }

    public String getLog_level()
    {
        return log_level;
    }

    public void setLog_level(String log_level)
    {
        this.log_level = log_level;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Test
    public void readAnno(){
        // 读取类的注释
        BaseLog obj = new BaseLog();
        MyTable table = obj.getClass().getAnnotation(MyTable.class); // 取得指定注释

        System.out.println("类注释（name）: " + table.name());
        System.out.println("类注释（version）: " + table.version());

        // 读取属性的注释
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields)
        {
            // Annotation[] arr2 = f.getAnnotations(); //得到所有注释
            MyField ff = f.getAnnotation(MyField.class);// 取得指定注释
            if(ff != null)
            {
                System.out.println("属性（" + f.getName() + "）: " + ff.name() + " -- " + ff.type());
            }
        }
    }
}