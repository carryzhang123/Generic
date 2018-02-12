import java.util.*;

/**
 * @author ZhangHang
 * @create 2017-12-07 12:19
 **/
public class Test {
    public static void main(String[] args) {
valueUpSort();

    }

    public static void valueUpSort() {
// 默认情况，TreeMap按key升序排序
        Map<String, Integer> map = new TreeMap<String, Integer>();
        map.put("acb1", 5);
        map.put("bac1", 3);
        map.put("bca1", 20);
        map.put("cab1", 80);
        map.put("cba1", 1);
        map.put("abc1", 10);
        map.put("abc2", 12);
// 升序比较器
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
// TODO Auto-generated method stub
                return o2.getValue() - o1.getValue();
            }
        };
// map转换成list进行排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
// 排序
        Collections.sort(list, valueComparator);
// 默认情况下，TreeMap对key进行升序排序
        System.out.println("------------map按照value升序排序--------------------");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
