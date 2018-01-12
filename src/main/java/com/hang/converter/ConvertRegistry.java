package com.hang.converter;

import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * @author ZhangHang
 * @create 2018-01-05 17:43
 **/
public interface ConvertRegistry {
    void addConvert(Converter<?,?> converter);

    void addConvert(GenericConverter converter);

    void addConvertFactory(ConverterFactory<?,?> converterFactory);

    void removeConvertible(Class<?> sourceType,Class<?> targetType);
}
