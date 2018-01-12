package com.hang.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author ZhangHang
 * @create 2018-01-05 17:53
 **/
public class MyConversionService implements ConversionService {
    @Autowired
    private GenericConversionService conversionService;
    private Set<?> converters;

    @PostConstruct
    public void afterPropertiesSet(){
    if(converters!=null){
        for(Object converter:converters){
            if(converter instanceof Converter<?,?>){
                conversionService.addConverter((org.springframework.core.convert.converter.Converter<?, ?>) converter);
            }else if(converter instanceof ConverterFactory<?,?>){
                conversionService.addConverterFactory((ConverterFactory<?, ?>) converter);
            }else if(converter instanceof GenericConverter){
                conversionService.addConverter((GenericConverter) converter);
            }
        }
    }
    }

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return conversionService.canConvert(sourceType,targetType);
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source,targetType);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return conversionService.canConvert(sourceType,targetType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return conversionService.convert(source,sourceType,targetType);
    }

    public Set<?> getConverters() {
        return converters;
    }

    public void setConverters(Set<?> converters){
        this.converters=converters;
    }
}
