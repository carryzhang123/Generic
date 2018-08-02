package hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:31
 * 具体构件
 **/
public class ComponentImpl implements Component {
    @Override
    public void sampleOperation() {
        System.out.print("I am a basic component!");
    }
}
