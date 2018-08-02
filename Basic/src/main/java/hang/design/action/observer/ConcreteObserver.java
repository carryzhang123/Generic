package hang.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-01-04 17:36
 **/
public class ConcreteObserver extends Observer {
    private int flag;
    private int number;

    public ConcreteObserver(int flag) {
        this.flag = flag;
    }

    @Override
    public void set(int state) {
        this.number = state;
    }

    @Override
    void update(int number) {
        this.number = number;
        say();
    }

    @Override
    public void say() {
        System.out.println("ClassName：" + this.getClass().getSimpleName() + flag + " 当前已修改主题参数：" + number);
    }
}
