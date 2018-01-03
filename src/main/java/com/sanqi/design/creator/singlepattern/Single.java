package com.sanqi.design.creator.singlepattern;

/**
 * @author ZhangHang
 * @create 2018-01-03 15:46
 * 单例模式-lazy模式结合静态内部类的initialize
 **/
public class Single {
    private Single single;

    /**
     * 防止被外部创建
     */
    private void single(){};

    /**
     * 静态内部类只有被首次访问时，才会进行初始化；
     * 类初始化时，会先初始化静态变量，再初始化静态代码，如果父类没有初始化，则会优先初始父类
     */
    private static class CreateSingle {
        private static Single single = new Single();
    }

    public static Single getPattern(){
        return CreateSingle.single;
    }
}
