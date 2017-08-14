/**
 * Created by CARRY on 2017/8/11.
 */
/**
 * compareTo(Object o)方法是java.lang.Comparable<T>接口中的方法，当需要对某个类的对象进行排序时，
 * 该类需要实现Comparable<T>接口的，必须重写public int compareTo(T o)方法
 */
/**
 * compare(Object o1,Object o2)方法是java.util.Comparator<T>接口的方法，
 * 它实际上是使用两对象的compareTo(object o)方法对两对象做对比
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
