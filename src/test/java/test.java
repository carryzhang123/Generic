import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author ZhangHang
 * @create 2017-08-19 17:42
 **/
public class test {
    public static void main(String[] args) {
        Student student=new Student(4);
        Student student2=new Student(41);
        Student student3=new Student(2);
        ArrayList<Student> list=new ArrayList<Student>();
        list.add(student);
        list.add(student2);
        list.add(student3);
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getId()-o1.getId();
            }
        });

        for(Student student1:list){
            System.out.println(student1.getId());
        }
    }
}


class Student{
    int id;

    public Student(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}