package com.hang.logfiltertools.tools;//package com.game.tools;
//
//import com.game.annotation.Init;
//import com.game.log.impl.LogFilter;
//
//import java.io.File;
//import java.io.FileFilter;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.List;
//
///**
// * @author ZhangHang
// * @create 2018-05-09 14:52
// **/
//public class LoaderAnnotation {
//
//    private static List<Class> clazzs = new ArrayList<Class>();
//    private static String packName = "com.game.log.impl.handler";
//
//    public static void invokeMethod() throws Exception {
//        getClasssFromPackage(packName);
//        for (Class c : clazzs) {
//            LogFilter logFilter = (LogFilter) c.newInstance();
//            Method[] methods = LogFilter.class.getDeclaredMethods();
//            for (Method method : methods) {
//                method.setAccessible(true);
//                if (method.isAnnotationPresent(Init.class)) {
//                    method.invoke(logFilter);
//                }
//            }
//        }
//    }
//
//    public static List<Class> getClasssFromPackage(String pack) {
//        // 是否循环搜索子包
//        boolean recursive = false;
//        // 包名字
//        String packageName = pack;
//        // 包名对应的路径名称
//        String packageDirName = packageName.replace('.', '/');
//        Enumeration<URL> dirs;
//        try {
//            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
//            while (dirs.hasMoreElements()) {
//                URL url = dirs.nextElement();
//                String protocol = url.getProtocol();
//                if ("file".equals(protocol)) {
////                    System.out.println("file类型的扫描");
//                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
//                    findClassInPackageByFile(packageName, filePath, recursive, clazzs);
//                } else if ("jar".equals(protocol)) {
////                    System.out.println("jar类型的扫描");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return clazzs;
//    }
//
//    /**
//     * 在package对应的路径下找到所有的class
//     *
//     * @param packageName package名称
//     * @param filePath    package对应的路径
//     * @param recursive   是否查找子package
//     * @param clazzs      找到class以后存放的集合
//     */
//    public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class> clazzs) {
//        File dir = new File(filePath);
//        if (!dir.exists() || !dir.isDirectory()) {
//            return;
//        }
//        // 在给定的目录下找到所有的文件，并且进行条件过滤
//        File[] dirFiles = dir.listFiles(new FileFilter() {
//
//            @Override
//            public boolean accept(File file) {
//                boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
//                boolean acceptClass = file.getName().endsWith("class");// 接受class文件
//                return acceptDir || acceptClass;
//            }
//        });
//
//        for (File file : dirFiles) {
//            if (file.isDirectory()) {
//                findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
//            } else {
//                String className = file.getName().substring(0, file.getName().length() - 6);
//                try {
//                    clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
