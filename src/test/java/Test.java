import com.hang.ramcache.anno.Cached;

/**
 * @author ZhangHang
 * @create 2017-12-07 12:19
 **/
public class Test {
    public static void main(String[] args) {
        try {
            Class c = Class.forName("com.hang.ramcache.anno.Cached");
            Cached cached = (Cached) c.getAnnotation(Cached.class);
            System.out.println(cached);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
