package com.hang.exception;

/**
 * @author ZhangHang
 * @create 2018-07-02 12:52
 **/
public class TestException {

    public static void main(String[] args) {
        TestException test = new TestException();
        try {
//            test.cast();
            test.ill();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cast() throws CastException {
        throw new CastException("user-defined Exception!");
    }

    public void ill() throws IllegalStateException {
        throw new IllegalStateException("ill Exception!");
    }

}
