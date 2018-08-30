import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author zhanghang
 * @Date 2018/8/30 下午6:15
 **/
public class DubboTest {
    @Test
    public void dubboProvider() throws IOException {
        ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("classpath:applicationcontext.xml");
        context.start();
        // 阻塞当前进程，否则程序会直接停止
        System.in.read();
    }
}
