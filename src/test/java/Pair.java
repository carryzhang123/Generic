/**
 * @author ZhangHang
 * @create 2017-08-01 20:59
 **/

/**
 * compareTo(Object o)方法是java.lang.Comparable<T>接口中的方法，当需要对某个类的对象进行排序时，
 * 该类需要实现Comparable<T>接口的，必须重写public int compareTo(T o)方法
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * compare(Object o1,Object o2)方法是java.util.Comparator<T>接口的方法，
 * 它实际上是使用两对象的compareTo(object o)方法对两对象做对比
 */
class Pair{
private final static Comparator<User> COMPARABLE=new Comparator<User>() {
    @Override
    public int compare(User o1, User o2) {
        return o1.compareTo(o2);
    }
};

    public static void main(String[] args) {
        ArrayList<User> list=new ArrayList<>();
        User user1=new User(1,"zhanghang");
        User user2=new User(2,"zhanghang");
        list.add(user1);
        list.add(user2);
        Collections.sort(list);
        for(User user:list){
            System.out.println(user.getId()+"--------"+user.getName());
        }
    }
}
