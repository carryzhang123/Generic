package hang.compare; /**
 * @author ZhangHang
 * @create 2017-08-01 20:59
 * 对TreeSet进行排序，只需要里面集合的compareTo()方法即可
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class CompareSet {
    public static void main(String[] args) {
        TreeSet<Student> treeSet=new TreeSet<Student>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getNumber()-o2.getNumber();
            }
        });
        Student student1=new Student(3,21);
        Student student2=new Student(33,22);
        Student student3=new Student(2,23);
        treeSet.add(student1);
        treeSet.add(student2);
        treeSet.add(student3);
        Iterator<Student> it=treeSet.iterator();
        while (it.hasNext()){
            Student student=it.next();
            System.out.println(student.getNumber()+"---------"+student.getAge());
        }
    }
}
