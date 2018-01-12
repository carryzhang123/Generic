package com.hang.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author ZhangHang
 * @create 2017-08-14 12:24
 * 对List集合进行排序，需要使用Collections.sort()方法
 **/
public class CompareList {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<Student>();
        Student student1=new Student(3,21);
        Student student2=new Student(33,22);
        Student student3=new Student(2,23);
        list.add(student1);
        list.add(student2);
        list.add(student3);
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getNumber()-o2.getNumber();
            }
        });
        for(Student student:list){
            System.out.println(student.getNumber()+"  ---   "+student.getAge());
        }
}}
