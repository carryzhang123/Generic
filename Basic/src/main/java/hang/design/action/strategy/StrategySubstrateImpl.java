package hang.design.action.strategy;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:40
 **/
public class StrategySubstrateImpl implements Strategy {
    private int flag;

    public StrategySubstrateImpl(int flag) {
        this.flag = flag;
    }

    @Override
    public float calculate(float price) {
        return (float) (price-2.5);
    }

    @Override
    public int getFlag() {
        return flag;
    }
}
