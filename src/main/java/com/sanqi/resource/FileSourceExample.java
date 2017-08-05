package com.sanqi.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ZhangHang
 * @create 2017-07-08 16:46
 **/
public class FileSourceExample {
    public static void main(String[] args) {
        try {
            String filePath="E:/workSpace/Generic/src/main/java/com/sanqi/resource/conf/file1.txt";

            //使用系统文件路径方式加载文件
            WritableResource res1=new PathResource(filePath);

            //使用类路径方式加载文件
            Resource res2=new ClassPathResource("/conf/file1.txt");

            //使用WritableResource接口写资源文件
            OutputStream stream1=res1.getOutputStream();
            stream1.write("hello world".getBytes());
            stream1.close();

            //使用Resource接口读资源文件
            InputStream ins1=res1.getInputStream();

            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int i;
            while ((i=ins1.read())!=-1){
                baos.write(i);
            }
            System.out.println(baos.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
