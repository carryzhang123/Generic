package hang.spring.sanqilaod.reflect;

import com.hang.spring.sanqilaod.annotation.ReceiveEvent;
import com.hang.spring.sanqilaod.event.IEvent;
import com.hang.spring.sanqilaod.facade.PetFacade;
import com.hang.tools.classes.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangHang
 * @create 2018-06-07 15:20
 **/
public class Receive {
    private static Map<Method, Class> eventMap = new HashMap<>();

    /**
     * 派发事件
     */
    public static void laodEvent() {
        List<Class> list = ClassUtil.getClasssFromPackage(PetFacade.class.getPackage().getName());
        for (Class clz : list) {
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(ReceiveEvent.class)) {
                    eventMap.put(method, clz);
                }
            }
        }
    }


    public static void detachEvent(IEvent event) throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Method, Class> entry : eventMap.entrySet()) {
            for (Class paramType : entry.getKey().getParameterTypes()) {
                if (event.getClass().equals(paramType)) {
                    entry.getKey().invoke(SpringContext.getInstance().getBeanOfType(entry.getValue()), event);
                }
            }
        }
    }
}
