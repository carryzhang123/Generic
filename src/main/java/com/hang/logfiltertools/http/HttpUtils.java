package com.hang.logfiltertools.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.*;

public final class HttpUtils {
    private static final int HTTP_SIZE = 4;
    private static ThreadPoolExecutor[] service = null;
    private static ExecutorService writeExecutor=null;

    static {
        service = new ThreadPoolExecutor[HTTP_SIZE];
        for (int i = 0; i < HTTP_SIZE; i++) {
            service[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                    new ThreadPoolExecutor.DiscardPolicy());
            service[i].prestartAllCoreThreads();
        }
        writeExecutor= Executors.newSingleThreadExecutor();
    }

    public static void addTask(long hashCode,Runnable runnable){
        service[(int)Math.abs(hashCode%HTTP_SIZE)].execute(runnable);
    }

    public static void addWriteTask(Runnable runnable){
        writeExecutor.execute(runnable);
    }

    public static void closeThreadPool(){
        for (int i = 0; i < HTTP_SIZE; i++) {
            service[i].shutdown();
        }
    }

    public static String httpRequest(String urlString, String method, Map<String,String> params){
        if("GET".equals(method)){
            return httpGetRequest(urlString,method);
        }else{
            return httpPostRequest(urlString,method,params);
        }
    }

    public static String makeContent(HttpURLConnection urlConnection,String result){
        StringBuilder temp = new StringBuilder();
        try{
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.write(result.getBytes());
            dos.flush();
            dos.close();

            int resultCode = urlConnection.getResponseCode();
            if(HttpURLConnection.HTTP_OK==resultCode){
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = bufferedReader.readLine();
                while(line!=null){
                    temp.append(line);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        }catch (IOException e){
            return null;
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
        }
        return temp.toString();
    }
    private static String httpPostRequest(String urlString, String method, Map<String,String> params){
        URL url;
        try{
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setUseCaches(false);
            StringBuilder urlParam = new StringBuilder();
            String result = "";
            if(params!=null){
                for(Map.Entry<String,String> entry:params.entrySet()){
                    urlParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                result = urlParam.substring(0,urlParam.length()-1);
            }
            return makeContent(urlConnection,result);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String httpGetRequest(String urlString, String method){
        URL url;
        try{
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setUseCaches(false);
            StringBuilder temp = new StringBuilder();
            try{
                int resultCode = urlConnection.getResponseCode();
                if(HttpURLConnection.HTTP_OK==resultCode){
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = bufferedReader.readLine();
                    while(line!=null){
                        temp.append(line);
                        line = bufferedReader.readLine();
                        if(line!=null){
                            temp.append("\r\n");
                        }
                    }
                    bufferedReader.close();
                }
            }catch (IOException e){
                return null;
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
            }
            return temp.toString();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
