package hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:35
 * 具体装饰角色：通过构件实现基础操作后，去实现自身操作
 **/
public class DecorateFish extends Decorate {

    public DecorateFish(Component component) {
        super(component);
    }

    @Override
    public void say() {
        super.sampleOperation();
        System.out.println("     ClassName:" + this.getClass().getSimpleName());
    }
}
