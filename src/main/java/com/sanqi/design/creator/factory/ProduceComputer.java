package com.sanqi.design.creator.factory;

/**
 * @author ZhangHang
 * @create 2018-01-03 17:57
 **/
public class ProduceComputer implements Produce {

    @Override
    public AMD makeAMDComputer() {
        return new AMD(1, 1);
    }

    @Override
    public Intel makeIntelComputer() {
        return new Intel(2, 2);
    }
}
