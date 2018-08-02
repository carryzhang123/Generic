package hang.annotation;

/**
 * @author ZhangHang
 * @create 2018-01-25 17:44
 **/

import com.hang.tools.time.TimeUtils;

import java.util.Date;

/**
 * 基础日志
 *
 */
@MyTable(name="T_BaseLog",version = "1.1")
public class BaseAnno {

    @MyField(name="addTime",type="Date")
    private Date log_time; // 时间

    @MyField(name="log_level",type="String")
    private String log_level; // 级别

    @MyField(name="message",type="String")
    private String message; // 日志内容

    @MyMethod(name = "date",effect = "out put time")
    public void dateTest(){
        System.out.println(TimeUtils.formatTime(new Date()));
    }

    @MyMethod(name = "level", effect = "set level")
    private void levelTest() {
        System.out.println(Math.round(5.0));
    }

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

}