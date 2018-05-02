package com.hang.io.serialize;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author ZhangHang
 * @create 2018-04-28 12:08
 **/
public class User implements Serializable{
    private transient String userName;

    @JsonIgnore
    private String password;

    private Address address;

    public User() {
    }

    public User(String userName, String password, String address) {
        this.userName = userName;
        this.password = password;
        this.address=new Address(address);
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
