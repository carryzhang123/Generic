import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZhangHang
 * @create 2017-08-02 10:46
 **/
public class Test {
    public static void main(String[] args) {
        String beginDate="82795932";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(beginDate)));
        System.out.println(sd);
    }
}
