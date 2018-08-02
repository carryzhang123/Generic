package com.hang.spring.sanqilaod;

import com.hang.spring.sanqilaod.event.PetEvent;
import com.hang.spring.sanqilaod.model.PetInfo;
import com.hang.spring.sanqilaod.reflect.SpringContext;
import com.hang.spring.sanqilaod.resource.PetResource;
import com.hang.tools.threadpool.ExecutorUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;

/**
 * @author ZhangHang
 * @create 2018-06-04 18:26
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        //启动Spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/applicationcontext.xml");

        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                for (PetResource petResource : SpringContext.getPetManager().getAll()) {
                    String level = petResource.getLevel();
                    String exp = petResource.getExp();

                    PetEvent petEvent = PetEvent.valueOf(PetInfo.valueOf(level, exp));

                    try {
                        SpringContext.getInstance().detachEvent(petEvent);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        },3000);

    }
}
