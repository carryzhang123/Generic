package hang.annotation;

import com.hang.thread.tiantask.EventBusManager;
import com.hang.thread.tiantask.RunnableKey;
import com.hang.thread.tiantask.TianEvent;

/**
 * @author ZhangHang
 * @create 2018-05-02 22:16
 **/
public class ReceiveMethod {
    @ReceiveEvent
    public void receiveTianEvent(TianEvent event){
        System.out.println("TianEvent id : "+ RunnableKey.values()[event.getThreadId()].getRunnableKeyName());
        EventBusManager.submit(event);
    }

    public void receiveTianEventNoAnnotation(TianEvent event){
        System.out.println("No Annotation TianEvent id : "+event.getThreadId());
    }
}
