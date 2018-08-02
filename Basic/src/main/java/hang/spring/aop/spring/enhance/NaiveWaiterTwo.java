package hang.spring.aop.spring.enhance;

import java.sql.SQLException;

/**
 * @author ZhangHang
 * @create 2017-07-12 15:08
 **/
public class NaiveWaiterTwo  {

    public void greetTo() {
        throw new RuntimeException("运行异常。");
    }

    public void serveTo() throws Exception{
        throw new SQLException("数据更新操作异常。");
    }
}
