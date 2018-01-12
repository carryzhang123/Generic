package com.hang.converter;

import org.springframework.core.convert.TypeDescriptor;

/**
 * @author ZhangHang
 * @create 2018-01-05 17:16
 **/
public interface ConversionService {
    boolean canConvert(Class<?> sourceType,Class<?> targetType);

    <T> T convert(Object source,Class<T> targetType);

    boolean canConvert(TypeDescriptor sourceType,TypeDescriptor targetType);

    Object convert(Object source,TypeDescriptor sourceType,TypeDescriptor targetType);
}
