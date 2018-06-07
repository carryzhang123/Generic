package com.hang.tools.classes;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author ZhangHang
 * @create 2018-05-16 11:47
 **/
public class ClassUtil {

//    /**
//     * 查询当前类或接口路径下所有实现的类
//     * @param clazz
//     * @return
//     */
//    public static ArrayList<Class> getAllClassByIntergace(Class clazz) {
//        ArrayList<Class> list = new ArrayList<>();
//        //判断是否一个接口
//        if (clazz.isInterface()) {
//            try {
//                ArrayList<Class> allClass = getAllClass(clazz.getPackage().getName());
//                /**
//                 * 循环判断路径下的所有类是否实现指定的接口，并排除接口自己
//                 */
//                for (int i = 0; i < allClass.size(); i++) {
//                    if (clazz.isAssignableFrom(allClass.get(i))) {
//                        //自身并不加进去
//                        if (!clazz.equals(allClass.get(i))) {
//                            list.add(allClass.get(i));
//                        } else {
//
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            //不是接口不做处理
//            ArrayList<Class> allClass = getAllClass(clazz.getPackage().getName());
//        }
//        return list;
//    }
//
//    /**
//     * 获取指定路径下的所有类
//     */
//    public static ArrayList<Class> getAllClass(String packagename) {
//        ArrayList<Class> list = new ArrayList<>();
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        String path = packagename.replace(".", "/");
//        try {
//            ArrayList<File> fileList = new ArrayList<>();
//            /**
//             * 使用的是相对路径，且路径中不可包含空格、特殊字符
//             */
//            Enumeration<URL> enumeration = classLoader.getResources(path);
//            while (enumeration.hasMoreElements()) {
//                URL url = enumeration.nextElement();
//                fileList.add(new File(url.getFile()));
//            }
//            for (int i = 0; i < fileList.size(); i++) {
//                list.addAll(findClass(fileList.get(i), packagename));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    /**
//     * 如果file是文件夹，则递归调用findClass方法，或者文件夹下的类，如果file本身是类文件，则加入list中进行保存，并返回
//     *
//     * @param file
//     * @param packagename
//     * @return
//     */
//    private static ArrayList<Class> findClass(File file, String packagename) {
//        ArrayList<Class> list = new ArrayList<>();
//        if (!file.exists()) {
//            return list;
//        }
//
//        File[] files = file.listFiles();
//        for (File file2 : files) {
//            if (file2.isDirectory()) {
//                //断言判断
//                assert !file2.getName().contains(".");
//                ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
//                list.addAll(arrayList);
//            } else if (file2.getName().endsWith(".class")) {
//                try {
//                    //保存的类文件不需要后缀.class
//                    list.add(Class.forName(packagename + "." + file2.getName().substring(0, file2.getName().length() - 6)));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return list;
//    }

    public static List<Class> getClasssFromPackage(String pack) {
         List<Class> clazzs = new ArrayList<Class>();
        // 是否循环搜索子包
        boolean recursive = false;
        // 包名字
        String packageName = pack;
        // 包名对应的路径名称
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
//                    System.out.println("file类型的扫描");
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassInPackageByFile(packageName, filePath, recursive, clazzs);
                } else if ("jar".equals(protocol)) {
//                    System.out.println("jar类型的扫描");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazzs;
    }

    /**
     * 在package对应的路径下找到所有的class
     *
     * @param packageName package名称
     * @param filePath    package对应的路径
     * @param recursive   是否查找子package
     * @param clazzs      找到class以后存放的集合
     */
    public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件过滤
        File[] dirFiles = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
                boolean acceptClass = file.getName().endsWith("class");// 接受class文件
                return acceptDir || acceptClass;
            }
        });

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
