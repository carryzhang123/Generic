package hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 16:09
 **/
public class DecorateHorse extends Decorate {
    public DecorateHorse(Component component) {
        super(component);
    }

    @Override
    public void say() {
        super.sampleOperation();
        System.out.println("     ClassName:" + this.getClass().getSimpleName());
    }
}
