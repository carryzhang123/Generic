package hang.design.creator.build;

/**
 * @author ZhangHang
 * @create 2018-06-03 18:58
 **/

/**
 * 1、采用的建造者模式，根据传入的参数，生成相应的数据；
 * 2、并且可以在valueOf方法里对数据进行判断处理，合适的添加上去；
 */
public class Builder {
    private int id;
    private int number;

    public static Builder valueOf(int id,int numberJ){
        Builder builder=new Builder();
        builder.setId(id);
        builder.setNumber(numberJ);
        return builder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
