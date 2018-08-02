package hang.design.action.observer;

/**
 * @author ZhangHang
 * @create 2018-05-28 23:08
 **/
public class Test {
    public static void main(String[] args) {
        Observer observer1=new ConcreteObserver(1);
        Observer observer2=new ConcreteObserver(2);
        Subject subject=new ConcreteSubject();
        //添加观察者
        subject.attachSubject(observer1);
        subject.attachSubject(observer2);
        //主题修改时，通知观察者
        subject.updateStatus(3);
    }
}
