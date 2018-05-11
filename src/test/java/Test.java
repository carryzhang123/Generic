import java.util.*;

/**
 * @author ZhangHang
 * @create 2017-12-07 12:19
 **/
public class Test {

    @org.junit.Test
    public void test() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        Iterator<Integer> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            int key = iterator.next();
            System.out.println(key);
            hashMap.remove(key);
        }
    }
}
