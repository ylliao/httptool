package com.starnet.httptool.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description OkHttpClient模拟发送请求
 * @author yueli.liao
 * @date Created in 2017/9/16
 * @version 1.0
 */
public class Okhttp3Client {
    /**
     * 创建一个全局的OkHttpClient，官方不建议创建多个OkHttpClient；
     * 如果有需要，可以使用clone方法，再进行自定义。
     */
    private final static OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.SECONDS)  // 设置连接超时时间
            .readTimeout(500, TimeUnit.SECONDS)     // 设置读取超时时间
            .build();

    /**
     * 发送Get同步请求，接收响应字符串
     *
     * @param reqUrl 请求URL
     * @return 响应字符串
     * @throws IOException
     */
    public static String getSync(String reqUrl) throws IOException {
        // 构建request
        Request request = new Request.Builder().url(reqUrl).build();
        return execute(request);
    }

    /**
     * 发送Get异步请求
     *
     * @param reqUrl 请求URL
     * @throws IOException
     */
    public static void getAsync(String reqUrl) throws IOException {
        // 构建request
        Request request = new Request.Builder().url(reqUrl).build();
        enqueue(request);
    }

    /**
     * 发送Post请求，接收响应字符串
     * 说明：参数类型为Map
     *
     * @param reqUrl 请求URL
     * @param map 参数列表
     * @return 响应字符串
     * @throws IOException
     */
    public static String post(String reqUrl, Map<String, String> map) throws IOException {
        // 封装参数
        FormBody.Builder formBoBuilder = new FormBody.Builder();
        Iterator iter = map.entrySet().iterator();
        Map.Entry<String, String> entry = null;
        while (iter.hasNext()) {
            entry = (Map.Entry<String, String>)iter.next();
            if (entry.getValue() == null) continue;
            formBoBuilder.add(entry.getKey(), entry.getValue());
        }

        // 构建request
        RequestBody body = formBoBuilder.build();
        Request request = new Request.Builder().url(reqUrl).post(body).build();

        return execute(request);
    }

    /**
     * 发送Post请求，接收响应字符串
     * 说明：参数为json字符串
     *
     * @param reqUrl 请求URL
     * @param json json字符串
     * @return 响应字符串
     * @throws IOException
     */
    public static String post(String reqUrl, String json) throws IOException {
        // 描述HTTP请求或响应主体的内容类型
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        // 构建request
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder().url(reqUrl).post(body).build();

        return execute(request);
    }

    /**
     * 同步线程访问网络，并返回响应结果
     *
     * @param request
     * @return 响应结果
     * @throws IOException
     */
    private static String execute(Request request) throws IOException {
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code : " + response);
            }
            return response.body().string();
        }
    }

    /**
     * 异步访问网络，处理返回结果
     * 说明：根据需求代替同步访问
     *
     * @param request
     * @throws IOException
     */
    private static void enqueue(Request request) throws IOException {
        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Access to cancel or handle failed : " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                // 打印请求头信息
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                System.out.println("Asyn response : " + response.body().string());
            }
        });
    }
}
