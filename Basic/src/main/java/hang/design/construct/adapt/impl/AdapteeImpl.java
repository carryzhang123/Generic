package hang.design.construct.adapt.impl;

import com.hang.design.construct.adapt.IAdaptee;

/**
 * @author ZhangHang
 * @create 2018-06-03 19:20
 **/
public class AdapteeImpl implements IAdaptee {
    @Override
    public void say() {
        System.out.println("I am source data!");
    }
}
