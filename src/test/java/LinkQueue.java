/**
 * @author ZhangHang
 * @create 2017-11-09 16:08
 * <p>
 * 链式队列
 **/
public class LinkQueue {
    private QueueNode begin;
    private QueueNode end;

    class QueueNode {
        int num;
        QueueNode next;

        public QueueNode(int num) {
            this.num = num;
        }
    }

    private void push(int num) {
        QueueNode node = new QueueNode(num);
        if (end == null) {
            end = node;
            begin = node;
            return;
        }
        end.next = node;
        end = node;
    }

    private void pop() {
        QueueNode node = begin;
        if (node == null) {
            System.out.println("空列");
            end = begin;
            return;
        }
        System.out.println(node.num);
        begin = begin.next;
    }

    public static void main(String[] args) {
        LinkQueue queue = new LinkQueue();
        queue.push(1);
        queue.pop();
        queue.pop();
        queue.push(13);
        queue.push(12);
        queue.pop();
        queue.pop();
    }
}
