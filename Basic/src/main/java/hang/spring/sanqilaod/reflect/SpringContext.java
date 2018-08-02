package hang.spring.sanqilaod.reflect;

import com.hang.spring.sanqilaod.event.IEvent;
import com.hang.spring.sanqilaod.manager.AccountManager;
import com.hang.spring.sanqilaod.manager.PetManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ZhangHang
 * @create 2018-06-05 15:07
 **/
@Component
public class SpringContext implements ApplicationContextAware {
    public static SpringContext instance;

    @PostConstruct
    public void init() throws Exception {
        instance =this;
        //加载Resource数据，转换过成对象
        Inject.loadFiles();
        //加载事件
        Receive.laodEvent();
    }

    public static SpringContext getInstance() {
        return instance;
    }

    @Autowired
    private AccountManager accountManager;

    public static AccountManager getAccountManager() {
        return instance.accountManager;
    }

    @Autowired
    private PetManager petManager;

    public static PetManager getPetManager(){
        return instance.petManager;
    }

    public void detachEvent(IEvent event) throws InvocationTargetException, IllegalAccessException {
        Receive.detachEvent(event);
    }

    /**
     * 通过容器获取benn实例
     * @param clz
     * @return
     */
    public Object getBeanOfType(Class<?> clz) {
        for(Map.Entry<String,?> entry:ctx.getBeansOfType(clz).entrySet()){
            return entry.getValue();
        }
        return null;
    }

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
