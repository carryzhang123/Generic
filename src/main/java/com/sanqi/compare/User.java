package com.sanqi.compare;
/**
 * Created by CARRY on 2017/8/11.
 */
/**
 * Comparator(Object o)是一个外比较器，当需要对集合里的类进行排序时，可以使用
 */
/**
 * Comparable(Object o1)是一个内比较器，类可以实现此接口的compareTo(....)方法，可以对类的本身做对比
 */
public class User implements Comparable{
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        //1：代表第一个大于第二个，-1：代表第一个小于第二个
        if(this==o){
            return 0;
        }else if(o!=null&& o instanceof User){
            User u= (User) o;
            if(this.id<u.id){
                return -1;
            }else {
                return 1;
            }
        }else {
            return -1;
        }
    }
}
