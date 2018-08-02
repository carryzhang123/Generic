package hang.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:35
 **/
public abstract class Observer {
    abstract void update(int parameter);

    abstract void set(int parameter);

    abstract void say();
}
