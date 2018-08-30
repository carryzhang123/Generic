import com.hang.dubbo.DemoService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author zhanghang
 * @Date 2018/8/30 下午6:13
 **/
public class DubboTest {

    @Test
    public void dubboConsumer() {
        ClassPathXmlApplicationContext context
                = new ClassPathXmlApplicationContext("classpath:spring/applicationcontext.xml");
        context.start();

        String username = "aotian";
        DemoService demoService = (DemoService) context.getBean("demoService");
        System.out.println(demoService.changeUserName(username));
    }
}
