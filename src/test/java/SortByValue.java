import java.util.*;

/**
 * @author ZhangHang
 * @create 2017-07-28 16:59
 **/
public class SortByValue {

    public static void main(String[] args) {
        TreeMap<Pair,Integer> treeMap=new TreeMap<Pair, Integer>();
        treeMap.put(new Pair("me",1000),1000);
        treeMap.put(new Pair("and",4000),2000);
        treeMap.put(new Pair("you",3000),3000);
        treeMap.put(new Pair("food",10009),4000);
        treeMap.put(new Pair("me",5000),5000);
//        Set<Pair> set = new TreeSet<Pair>();
//        set.add(new Pair("me", "1000"));
//        set.add(new Pair("and", "4000"));
//        set.add(new Pair("you", "3000"));
//        set.add(new Pair("food", "10000"));
//        set.add(new Pair("hungry", "5000"));
//        set.add(new Pair("later", "6000"));
//        set.add(new Pair("myself", "1000"));
//        for (Iterator<Pair> i = set.iterator(); i.hasNext();)
//            // 我喜欢这个for语句
//            System.out.println(i.next());
        for(Map.Entry<Pair,Integer> entry:treeMap.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
