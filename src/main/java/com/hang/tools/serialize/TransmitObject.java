package com.hang.tools.serialize;

import com.hang.tools.serialize.JsonUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author ZhangHang
 * @create 2018-04-28 12:13
 **/

public class TransmitObject {

    /**
     * 1、序列化时只对对象的状态进行保存，不会管对象的方法
     * 2、父类如果序列化，子类也会默认序列化
     * 3、一个对象会序列化它的成员变量，并保存成员变量的数据
     * 4、private transient static修饰的对象不能序列化，Socket及Thread也无法序列化
     * 5、序列化运行时使用一个称为serialVersionUID 的版本号与每个可序列化类相关联，
     */
    @Test
    public void serialize() {
        try {
            //序列化
            FileOutputStream fos = new FileOutputStream("F://object.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            User user1 = new User("zhanghang", "123456", "长沙");
            oos.writeObject(user1);
            oos.flush();
            oos.close();
            //反序列化
            FileInputStream fis = new FileInputStream("F://object.out");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User user2 = (User) ois.readObject();
            System.out.println(user2.getUserName() + " " + user2.getPassword() + "  " + user2.getAddress().getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象序列化json
     * 1、和Java自带的serialize不同，jackson不需要对象继承serialize接口
     * 2、JsonIgnore是表示忽略不记的
     */
    @Test
    public void jsonSerialize() {
        try {
            //序列化
            FileOutputStream fos = new FileOutputStream("F://object.json");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            User user1 = new User("zhanghang", "123456", "ChangSha");
            oos.writeObject(JsonUtils.object2String(user1));
            oos.flush();
            oos.close();

            //反序列化
            FileInputStream fis = new FileInputStream("F://object.json");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String str = (String) ois.readObject();
            User user=JsonUtils.string2Object(str,User.class);
            System.out.println(user.getUserName()+"  "+user.getPassword()+"  "+user.getAddress().getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class User implements Serializable {
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
}
