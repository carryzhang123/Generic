package com.hang.io.serialize;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-04-28 14:11
 **/
public class Address implements Serializable{
    private String address;

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
