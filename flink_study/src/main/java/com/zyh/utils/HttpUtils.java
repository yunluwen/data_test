package com.zyh.utils;

import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

/**
 *  远程接口调用测试
 */
public class HttpUtils {

    public static void main(String[] args) throws Exception{
        HttpResponse<JsonNode> jsonResponse =
                Unirest.get("http://10.33.15.106:6666/metrics")  //请求的url
                //.queryString("name","zhangyunhao")     //传递参数
                        .asJson();                                 //转换为json

        JsonNode jsonNode = jsonResponse.getBody();
        JSONObject obj = jsonNode.getObject();
        Optional<JSONObject> op = Optional.of(obj);

        if(op.isPresent()) {
            System.out.println(obj);
        }
//        System.out.println(obj.getString("name"));
//        System.out.println(obj.getInt("code"));
    }
}
