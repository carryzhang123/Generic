package com.sanqi.resource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author ZhangHang
 * @create 2017-07-08 17:48
 **/
public class PatternResolveTest {
    public static void main(String[] args) throws Throwable{
        ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        //加载所有类包com.sanqi（及子孙包）下以.xml为后缀的资源
        Resource[] resources=resolver.getResources("classpath*:com/sanqi/**/*.xml");
        for (Resource resource:resources){
            System.out.println(resource.getDescription());
        }
    }
}
