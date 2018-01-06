package com.sanqi.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-01-05 17:02
 **/
public class StringArrayToAttachmenList implements Converter<String[],List<Attachmen>> {
    @Override
    public List<Attachmen> converse(String[] source) {
        if(source==null){
            return null;
        }
        List<Attachmen> list=new ArrayList<Attachmen>(source.length);
        for(String attachStr:source){
            String[] attachInfos=attachStr.split(",");
            if(attachInfos.length!=3){
                throw new RuntimeException();
            }
            String name=attachInfos[0];
            String requestInfos=attachInfos[1];
            int size=Integer.parseInt(attachInfos[2]);
            list.add(new Attachmen(name,requestInfos,size));
        }
        return list;
    }
}
