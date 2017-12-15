/**
 * @author ZhangHang
 * @create 2017-11-09 15:33
 * <p>
 * 链式桟
 **/
public class LinkStack {

    private int size;
    private StackNode top;

    class StackNode {
        private int node;
        private StackNode next;

        public StackNode(int node) {
            this.node = node;
        }
    }

    public void push(int num) {
        StackNode node = new StackNode(num);
        node.next = top;
        top = node;
    }

    public void pop() {
        if (top == null) {
            System.out.println("已到桟尾");
            return;
        }

        System.out.println(top.node);
        top = top.next;
    }

    public static void main(String[] args) {
        LinkStack stack = new LinkStack();
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.push(42);
        stack.pop();
        stack.pop();
        stack.pop();
    }
}
