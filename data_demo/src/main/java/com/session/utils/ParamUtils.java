package com.session.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.session.conf.ConfigurationManager;
import com.session.constants.Constants;

public class ParamUtils {
    //从用户输入的参数中获取taskid，测试时用户只需输入taskid
    //从数据库中获取完整的task内容
    public static Long getTaskIdFromArgs(String args, String taskType) {

        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
        if(local) {
            return Long.valueOf(args);
        }
        return 1L;
    }

    /**
     * 从JSON对象中提取参数
     * @param jsonObject JSON对象
     * @return 参数
     */
    public static String getParam(JSONObject jsonObject, String field) {
        JSONArray jsonArray = jsonObject.getJSONArray(field);
        if(jsonArray != null && jsonArray.size() > 0) {
            return jsonArray.getString(0);
        }
        return null;
    }


}