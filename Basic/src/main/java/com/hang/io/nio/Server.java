package com.hang.io.nio;

import com.hang.tools.threadpool.ExecutorUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ZhangHang
 * @create 2018-05-26 15:21
 **/

/**
 * 1、打开Selector
 * 2、创建ServerSocketChannel通道，绑定端口，设置非阻塞，并注册到Selector相应的事件
 * 3、一次只能绑定一个事件，或者用|绑定多个事件
 * 4、ByteBuffer是nio缓存区，不管读或写，都需要先操作缓冲区，在写入或从流中读取
 * 5、nio是基于http的，http是无状态、无连接的，所以每次新的请求都是一个新的channel，每次从select取出来后，要进行删除
 */
public class Server {
    private Selector selector;
    // 设置缓存区大小
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

    private String str;

    public void start() throws Exception {
        //打开服务器套接字通道，创建Channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //服务器配置为非阻塞
        ssc.configureBlocking(false);
        //打开Selecetor
        selector = Selector.open();
        //注册到selector，等待传递
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        // 进行服务的绑定
        ssc.bind(new InetSocketAddress("127.0.0.1", 8081));

        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                try {
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator=keys.iterator();
                    while (keyIterator.hasNext()){
                        SelectionKey key=keyIterator.next();
                        keyIterator.remove();
                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isAcceptable()) {
                            accept(key);
                        } else if (key.isReadable()) {
                            read(key);
                        } else if (key.isWritable()) {
                            write(key);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0,1000);
    }

    /**
     * 监听到Accept事件处理
     *
     * @param key
     * @throws Exception
     */
    private void accept(SelectionKey key) throws Exception {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = ssc.accept();
        clientChannel.configureBlocking(false);
        System.out.println("server accept:" + clientChannel.getLocalAddress());
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 监听写事件，发送数据
     *
     * @param key
     * @throws Exception
     */
    private void write(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();
        System.out.println("server write:" + str);

        sendBuffer.clear();
        sendBuffer.put(str.getBytes());
        sendBuffer.flip();
        channel.write(sendBuffer);
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

        str = new String(readBuffer.array(), 0, numRead);
        System.out.println("server read:" + str);
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }
}
