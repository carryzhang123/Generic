/**
 * @author ZhangHang
 * @create 2017-11-09 15:19
 *
 * 顺序桟
 **/
public class SequenceStack {
    //数组下标
    private int capacity=-1;
    private Integer[] num=new Integer[10];
    public boolean push(int a){
        if(++capacity==num.length){
            System.out.println("已到桟尾");
            return false;
        }
        num[capacity]=a;
        return true;
    }

    public void pop(){
        if(capacity<0){
            System.out.println("已到栈顶");
            return ;
        }
        int tem=num[capacity--];
        System.out.println("当前元素："+tem);
    }

    public static void main(String[] args) {
        SequenceStack stack=new SequenceStack();
        stack.push(1);
        stack.push(3);
        stack.pop();
        stack.push(4);
        stack.pop();
        stack.pop();
        stack.pop();
    }
}
