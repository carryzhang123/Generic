/**
 * Created by CARRY on 2017/8/11.
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
