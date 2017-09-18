package com.starnet.httptool.joddhttp;

/**
 * @description
 * @author yueli.liao
 * @version 1.0
 * @date Created in 2017/9/16
 */
public class JoddHttpClient {

    /**
     * 发送Get请求，接收响应字符串
     * 注：也可以增加参数
     *
     * @param url 请求URL
     * @return 响应字符串
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        HttpResponse response = HttpRequest
                .get(url)
                .send();

        String result = null;
        if (response.statusCode() == 200) {
            response.charset("UTF-8");
            result = response.bodyText();
        }
        response.close();
        return result;
    }

     /**
     * 发送Post请求，接收响应字符串
     * 注：多参数请求
     *
     * @param url 请求URL
     * @param map 参数列表
     * @return 响应字符串
     * @throws IOException
     */
    public static String post(String url, Map<String, String> map) throws IOException {
        HttpResponse response = HttpRequest
                .post(url)
                .query(map)
                .send();
        String result = null;
        if (response.statusCode() == 200) {
            result = response.bodyText();
        }
        response.close();
        return result;
    }

}
