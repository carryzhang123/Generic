package hang.spring.aop.spring.section;

/**
 * @author ZhangHang
 * @create 2017-07-12 20:57
 **/
public class Waiter {
    public void greetTo(String name){
        System.out.println("waiter greet to "+name+"...");
    }
    public void serveTo(String name){
        System.out.println("waiter serving "+name+"...");
    }
}

