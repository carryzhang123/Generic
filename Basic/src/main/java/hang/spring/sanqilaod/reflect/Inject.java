package hang.spring.sanqilaod.reflect;

import com.hang.spring.sanqilaod.annotation.Id;
import com.hang.spring.sanqilaod.annotation.Manager;
import com.hang.spring.sanqilaod.annotation.Static;
import com.hang.spring.sanqilaod.manager.AccountManager;
import com.hang.tools.classes.ClassUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangHang
 * @create 2018-06-04 19:58
 **/
public class Inject {
    private static Map<String, Object> objects = new HashMap<>();
    private static Map<String, File> files = new HashMap<>();

    /**
     * 加载所有Resource文件
     * @throws Exception
     */
    public static void loadFiles() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:excels/*.xlsx");
            for (Resource resource : resources) {
                files.put(resource.getFilename().substring(0, resource.getFilename().indexOf(".")), resource.getFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanStatics();
    }

    /**
     * 获取Manager注解的类
     */
    private static void scanStatics() throws Exception {
        List<Class> list = ClassUtil.getClasssFromPackage(AccountManager.class.getPackage().getName());
        for (Class clz : list) {
            Storge<String, Object> storge = null;
            if (clz.isAnnotationPresent(Manager.class)) {
                Object manager = SpringContext.getInstance().getBeanOfType(clz);
                for (Field field : clz.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Static.class)) {
                        Type type = field.getGenericType();
                        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                            Type[] genes = ((ParameterizedType) type).getActualTypeArguments();
                            storge = injectValue(files.get(((Class<?>) genes[1]).getSimpleName()), (Class<?>) genes[1]);
                        }
                        field.set(manager, storge);
                    }
                }
                objects.put(clz.getSimpleName(), manager);
            }
        }
    }

    /**
     * 将Rsource表格数据转换成对象
     *
     * @param file
     * @param clz
     */
    private static Storge<String, Object> injectValue(File file, Class<?> clz) throws Exception {
        Storge<String, Object> storge = new Storge<String, Object>();
        Map<Integer, String> map = new HashMap<>();

        Sheet sheet = WorkbookFactory.create(file).getSheetAt(0);
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            map.put(i, sheet.getRow(0).getCell(i).getStringCellValue());
        }

        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Object resource = clz.newInstance();
            String key = null;
            Row row = sheet.getRow(i);
            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                for (Field field : clz.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.getName().equals(map.get(cellIndex))) {
                        if (field.isAnnotationPresent(Id.class)) {
                            key = row.getCell(cellIndex).getStringCellValue();
                            field.set(resource, key);
                        } else {
                            field.set(resource, row.getCell(cellIndex).getStringCellValue());
                        }
                    }
                }
            }
            storge.put(key, resource);
        }
        return storge;
    }
}
