package com.sanqi.compare;

/**
 * @author ZhangHang
 * @create 2017-09-05 14:51
 * 重写equals和hashCode方法，如把对象存入HashTable的集合中，则先会计算hashcode，得到区域，在对区域中进行遍历，查看是否相等
 * equals相等，hashcode一定相等，hashcode相等，equals不一定相等
 **/
public class Student {
    private int number;
    private int age;

   public Student(int number,int age){
        this.number=number;
        this.age=age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        int hash=7;
        hash=31*hash+number;
        hash=31*hash+age;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this)
            return true;
        if(obj==null)
            return false;
        if(getClass()!=obj.getClass())
            return false;
        Student student= (Student) obj;
        if(student.number!=this.number)
            return false;
        if(student.age!=this.age)
            return false;
        return true;
    }
}
