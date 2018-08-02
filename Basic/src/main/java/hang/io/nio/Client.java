package hang.io.nio;

import com.hang.tools.threadpool.ExecutorUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ZhangHang
 * @create 2018-05-26 16:11
 **/

/**
 * 1、打开Selector
 * 2、创建SocketChannel通道，绑定端口，设置非阻塞，并注册到Selector相应的事件
 * 3、一次只能绑定一个事件，或者用|绑定多个事件
 * 4、ByteBuffer是nio缓存区，不管读或写，都需要先操作缓冲区，在写入或从流中读取
 */
public class Client {
    private Selector selector;
    private ByteBuffer sendBuffer =ByteBuffer.allocate(1024);
    private ByteBuffer readBuffer=ByteBuffer.allocate(1024);
    private String message="This is nio test  !";

    public void start() throws Exception{
        //打开Socket通道
        SocketChannel sc=SocketChannel.open();
        //设置非阻塞
        sc.configureBlocking(false);
        //打开选择器
         selector=Selector.open();
        //注册连接服务器的动作
        sc.register(selector, SelectionKey.OP_CONNECT);
        //连接服务器地址和端口
        sc.connect(new InetSocketAddress("127.0.0.1",8081));

        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                try {
                    //轮询select上的通道
                    selector.select();
                    //返回此选择器已就绪操作
                    Set<SelectionKey> keys=selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator=keys.iterator();
                    while (keyIterator.hasNext()){
                        SelectionKey key=keyIterator.next();
                        keyIterator.remove();
                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isConnectable()) {
                            sc.finishConnect();
                            sc.register(selector, SelectionKey.OP_WRITE);
                        } else if (key.isWritable()) {
                            write(key);
                        } else if (key.isReadable()) {
                            read(key);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },0,3000);
    }

    /**
     * 监听写事件，发送数据
     *
     * @param key
     * @throws Exception
     */
    private void write(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();
        System.out.println("client write:" + message);
        sendBuffer.clear();
        sendBuffer.put(message.getBytes());
        sendBuffer.flip();
        channel.write(sendBuffer);
        //一种channel只能注册一种事件，可使用|来注册多个
        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 监听读事件，接受数据
     *
     * @param key
     * @throws Exception
     */
    private void read(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        int numRead = socketChannel.read(readBuffer);

        message = new String(readBuffer.array(), 0, numRead);
        System.out.println("client read:" + message);
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }
}
