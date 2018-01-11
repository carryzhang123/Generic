package com.hang.design.construct.facade;

/**
 * @author ZhangHang
 * @create 2018-01-04 18:25
 *外观类，将其它的类耦合到这个类里面
 **/
public class Facade {
    ModuleA moduleA=new ModuleA();
    ModuleB moduleB=new ModuleB();
    ModuleC moduleC=new ModuleC();

    public void say(){
        moduleA.sayA();
        moduleB.sayB();
        moduleC.sayC();
    }
}
