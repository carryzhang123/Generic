package com.hang.tools.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangHang
 * @create 2018-05-16 15:03
 **/

/**
 * 1、URL 连接可用于输入和/或输出；
 * 2、如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true。 conn.getInputStread().read()
 * 3、如果打算使用 URL 连接进行输出，则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。 conn.getOutputStream().write()
 */
public class HttpUtils {
    //给定一个http请求的线程池数量
    private static int HTTP_SIZE = 4;
    private static ThreadPoolExecutor[] service = null;

    static {
        service = new ThreadPoolExecutor[HTTP_SIZE];
        for (int i = 0; i < HTTP_SIZE; i++) {
            service[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
            service[i].prestartAllCoreThreads();
        }
    }

    /**
     * 添加任务
     *
     * @param hashcode
     * @param command
     */
    public static void addTask(long hashcode, Runnable command) {
        service[(int) Math.abs(hashcode % HTTP_SIZE)].execute(command);
    }

    public static String httpResuest(String urlString, String method, Map<String, String> params) {
        URL url;
        try {
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Charset", "UTF-8");

            //设置超时
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);

            urlConnection.setUseCaches(false);

            //设置必要参数
            StringBuilder urlParam = new StringBuilder();
            String result = "";
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                result = urlParam.substring(0, urlParam.length() - 1);
            }
            return makeContent(urlConnection, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String makeContent(HttpURLConnection urlConnection, String result) {
        StringBuilder temp = new StringBuilder();
        try {
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.write(result.getBytes());
            dos.close();

            //获取响应状态
            int resultCode = urlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = bufferedReader.readLine();
                while (line != null) {
                    temp.append(line);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return temp.toString();
    }
}
