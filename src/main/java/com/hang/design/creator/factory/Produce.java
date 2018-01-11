package com.hang.design.creator.factory;

/**
 * @author ZhangHang
 * @create 2018-01-03 17:48
 **/
public interface Produce {
    AMD makeAMDComputer();

    Intel makeIntelComputer();
}
