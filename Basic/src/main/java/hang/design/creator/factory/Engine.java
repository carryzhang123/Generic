package hang.design.creator.factory;

/**
 * @author ZhangHang
 * @create 2018-01-03 17:46
 * 系统有多个产品族，而系统知消耗某一种；
 * 编写多个产品族的创建类，并通过制造类Produce去产生；
 * 用户想要某种产品是，只需通过Produce类去生产指定的产品，不需要传入任何参数；
 **/
public class Engine {
    public static void main(String[] args) {
        //获得制造产品抽象类
        Produce produce = new ProduceComputer();
        //选择要制造的产品
        AMD amd=produce.makeAMDComputer();
        amd.calculate();

        Intel intel=produce.makeIntelComputer();
        intel.calculate();
    }
}
