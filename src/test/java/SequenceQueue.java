/**
 * @author ZhangHang
 * @create 2017-11-09 18:10
 **/
public class SequenceQueue {
    private Integer[] nums;

    public SequenceQueue() {
        nums = new Integer[10];
    }

    private int begin;
    private int end;

    public void push(int num) {
        if ( (end + 1) % nums.length == begin) {
            System.out.println("已满！");
        }
        end = (end + 1) % nums.length;
        nums[end] = num;
    }

    public void pop() {
        if (begin == end) {
            System.out.println("空！");
            return;
        }

        begin = (begin + 1) % nums.length;
        System.out.println(nums[begin]);
    }

    public static void main(String[] args) {
        SequenceQueue queue = new SequenceQueue();
        queue.push(1);
        queue.pop();
        queue.pop();
        queue.push(13);
        queue.push(12);
        queue.pop();
        queue.pop();
    }
}
