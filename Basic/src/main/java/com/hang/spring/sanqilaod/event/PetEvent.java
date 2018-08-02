package com.hang.spring.sanqilaod.event;

/**
 * @author ZhangHang
 * @create 2018-06-07 15:40
 **/
public class PetEvent implements IEvent{
    private Object object;

    public static PetEvent valueOf(Object object) {
        PetEvent event = new PetEvent();
        event.setObject(object);
        return event;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
