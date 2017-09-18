package com.starnet.httptool;

import com.starnet.httptool.okhttp.Okhttp3Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 测试类
 * @author yueli.liao
 * @version 1.0
 * @date Created in 2017/9/17
 */
public class ClientTest {

    public static void main(String[] args) throws IOException {
        // get请求
        String reqUrl = "http://localhost:8099/weather/cityId/101280601";
        String result = Okhttp3Client.getSync(reqUrl);
        System.out.println(result);

        // post请求
//        String reqUrl = "http://localhost:8099/weather/cityName";
//        Map<String, String> param = new HashMap<>();
//        param.put("cityName", "武汉");
//        String result = Okhttp3Client.post(reqUrl, param);
//        System.out.println(result);
    }
}
