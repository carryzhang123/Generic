package hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:43
 *抽象接口是规定事物的本质，具体接口实现抽象接口的基本功能；
 * 抽象装饰与抽象接口结合起来，可以实现基本的接口功能；
 * 具体装饰继承抽象装饰，可以在实现基本功能上，添加自己独有的功能；
 **/
public class Client {
    public static void main(String[] args) {
        Component component=new ComponentImpl();
        DecorateFish fishDecorate=new DecorateFish(component);
        fishDecorate.sampleOperation();
        DecorateHorse horseDecorate=new DecorateHorse(component);
        horseDecorate.sampleOperation();
    }
}
