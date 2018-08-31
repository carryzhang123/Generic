import com.hang.dubbo.DemoService;
import com.hang.exception.CastException;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @Author zhanghang
 * @Date 2018/8/30 下午6:13
 **/
@Slf4j
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


    public class callz implements Callable {
        private int i;

        public callz(int i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            if (i == 1) {
                throw new CastException("This is call thread exception!");
            }
            return i;
        }
    }

    @Test
    public void testCall() {
        FutureTask taskFuture=new FutureTask<Integer>(new callz(1));
        taskFuture.run();
        try {
            System.out.println(taskFuture.get());
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
