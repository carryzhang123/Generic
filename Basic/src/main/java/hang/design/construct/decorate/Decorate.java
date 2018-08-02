package hang.design.construct.decorate;

/**
 * @author ZhangHang
 * @create 2018-01-04 15:33
 * 装饰角色：将基础操作交给构件本身去处理
 **/
public abstract class Decorate implements Component {
    private Component component;

    public Decorate(Component component) {
        this.component = component;
    }

    @Override
    public void sampleOperation() {
        //委托给构件去操作
        component.sampleOperation();
    }

    public abstract void say();
}
