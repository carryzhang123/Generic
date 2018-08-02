package hang.ramcache.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-01-20 15:27
 **/
public class ReflectionUtility {
    public static Field[] getDeclaredFieldsWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
        List<Field> fields = new ArrayList<Field>();
        Field[] var6;
        int var5 = (var6 = clz.getDeclaredFields()).length;
        for (int var4 = 0; var4 < var5; var4++) {
            Field field = var6[var4];
            if (field.isAnnotationPresent(annotationClass)) {
                fields.add(field);
            }
        }
        return fields.toArray(new Field[0]);
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) &&
                !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
}
