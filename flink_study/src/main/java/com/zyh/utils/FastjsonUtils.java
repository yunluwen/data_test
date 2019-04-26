package com.zyh.utils;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.table.api.scala.map;

import java.util.*;


/**
 * json解析
 */

public class FastjsonUtils {

    public static void main(String[] args) {
//        String jsonStr2 = "{\"msec\":{\"value\":null,\"type\":\"string\",\"name\":\"msec\"}," +
//                "\"time_iso8601\":{\"value\":null,\"type\":\"string\",\"name\":\"time_iso8601\"}," +
//                "\"remote_addr\":{\"value\":\"127.0.0.1\",\"type\":\"string\",\"name\":\"remote_addr\"}," +
//                "\"query_string\":{\"value\":null,\"type\":\"string\",\"name\":\"query_string\"}," +
//                "\"http_x_forwarded_for\":{\"value\":null,\"type\":\"string\",\"name\":\"http_x_forwarded_for\"}," +
//                "\"http_user_agent\":{\"value\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36\",\"type\":\"string\",\"name\":\"http_user_agent\"},\"http_referer\":{\"value\":null,\"type\":\"string\",\"name\":\"http_referer\"},\"time_local\":{\"value\":\"07/Nov/2018:13:24:38 +0800\",\"type\":\"string\",\"name\":\"time_local\"},\"request\":{\"value\":\"GET / HTTP/1.1\",\"type\":\"string\",\"name\":\"request\"},\"status\":{\"value\":\"304\",\"type\":\"string\",\"name\":\"status\"},\"request_time\":{\"value\":null,\"type\":\"string\",\"name\":\"request_time\"},\"request_length\":{\"value\":null,\"type\":\"string\",\"name\":\"request_length\"},\"pipe\":{\"value\":null,\"type\":\"string\",\"name\":\"pipe\"},\"connection\":{\"value\":null,\"type\":\"string\",\"name\":\"connection\"},\"bytes_sent\":{\"value\":null,\"type\":\"string\",\"name\":\"bytes_sent\"},\"body_bytes_sent\":{\"value\":\"0\",\"type\":\"string\",\"name\":\"body_bytes_sent\"},\"date\":{\"value\":null,\"type\":\"string\",\"name\":\"date\"},\"timestamp\":{\"value\":null,\"type\":\"string\",\"name\":\"timestamp\"},\"ip\":{\"value\":2130706433,\"type\":\"string\",\"name\":\"ip\"},\"ip_str\":{\"value\":\"127.0.0.1\",\"type\":\"string\",\"name\":\"ip_str\"},\"remote_user\":{\"value\":null,\"type\":\"string\",\"name\":\"remote_user\"}}";
//        Map<String,String> map = parseColumns(jsonStr);
//        System.out.println(map.get("hbase"));
//        System.out.println(map.get("hive"));
//        testFastJson();
        String jsonStr = "{\n" +
                "     \"house.house\": {\n" +
                "     \"area\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"watch_times\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"create_time\": {\n" +
                "     \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "     \"COLUMN_TYPE\": \"datetime\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"city_en_name\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"total_floor\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"title\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"room\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"cover\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"last_update_time\": {\n" +
                "     \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "     \"COLUMN_TYPE\": \"datetime\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"region_en_name\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"price\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"build_year\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(4)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"street\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"district\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"admin_id\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"parlour\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"distance_to_subway\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"id\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"PRI\"\n" +
                "     },\n" +
                "     \"floor\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"bathroom\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"status\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(4)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"direction\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     }\n" +
                "     },\n" +
                "     \"house.user\": {\n" +
                "     \"last_update_time\": {\n" +
                "     \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "     \"COLUMN_TYPE\": \"datetime\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"password\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"create_time\": {\n" +
                "     \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "     \"COLUMN_TYPE\": \"timestamp\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"last_login_time\": {\n" +
                "     \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "     \"COLUMN_TYPE\": \"datetime\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"name\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"UNI\"\n" +
                "     },\n" +
                "     \"phone_number\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(15)\",\n" +
                "     \"COLUMN_KEY\": \"UNI\"\n" +
                "     },\n" +
                "     \"id\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(11)\",\n" +
                "     \"COLUMN_KEY\": \"PRI\"\n" +
                "     },\n" +
                "     \"avatar\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(255)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     },\n" +
                "     \"email\": {\n" +
                "     \"HIVE_TYPE\": \"string\",\n" +
                "     \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "     \"COLUMN_KEY\": \"UNI\"\n" +
                "     },\n" +
                "     \"status\": {\n" +
                "     \"HIVE_TYPE\": \"bigint,string,int,integer\",\n" +
                "     \"COLUMN_TYPE\": \"int(2)\",\n" +
                "     \"COLUMN_KEY\": \"\"\n" +
                "     }\n" +
                "     }\n" +
                "     }";
//        Map<String,Map<String,String>> map = parseColumnsTest(jsonStr);
//        System.out.println(map.get("house.house"));
//        System.out.println(map.get("house.user"));

        String string = "[\n" +
                "        {\n" +
                "            \"children\": [\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"id\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"PRI\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"title\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"price\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"area\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"room\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"floor\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"total_floor\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"watch_times\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"build_year\",\n" +
                "                    \"COLUMN_TYPE\": \"int(4)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"status\",\n" +
                "                    \"COLUMN_TYPE\": \"int(4)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "                    \"COLUMN_NAME\": \"create_time\",\n" +
                "                    \"COLUMN_TYPE\": \"datetime\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "                    \"COLUMN_NAME\": \"last_update_time\",\n" +
                "                    \"COLUMN_TYPE\": \"datetime\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"city_en_name\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"region_en_name\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"cover\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"direction\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"distance_to_subway\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"parlour\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"district\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"admin_id\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"bathroom\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"street\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"pri\": \"PRI\",\n" +
                "            \"tablename\": \"house.house\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"children\": [\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"id\",\n" +
                "                    \"COLUMN_TYPE\": \"int(11)\",\n" +
                "                    \"COLUMN_KEY\": \"PRI\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"name\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"UNI\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"password\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"email\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(32)\",\n" +
                "                    \"COLUMN_KEY\": \"UNI\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"phone_number\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(15)\",\n" +
                "                    \"COLUMN_KEY\": \"UNI\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"int,integer,bigint,string\",\n" +
                "                    \"COLUMN_NAME\": \"status\",\n" +
                "                    \"COLUMN_TYPE\": \"int(2)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"string\",\n" +
                "                    \"COLUMN_NAME\": \"avatar\",\n" +
                "                    \"COLUMN_TYPE\": \"varchar(255)\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "                    \"COLUMN_NAME\": \"create_time\",\n" +
                "                    \"COLUMN_TYPE\": \"timestamp\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "                    \"COLUMN_NAME\": \"last_login_time\",\n" +
                "                    \"COLUMN_TYPE\": \"datetime\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"HIVE_TYPE\": \"timestamp,string\",\n" +
                "                    \"COLUMN_NAME\": \"last_update_time\",\n" +
                "                    \"COLUMN_TYPE\": \"datetime\",\n" +
                "                    \"COLUMN_KEY\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"pri\": \"PRI\",\n" +
                "            \"tablename\": \"house.user\"\n" +
                "        }\n" +
                "    ]";
//        testFast(string);

        Map<String,Map<String,String>> map = parseColumnsTest(string);
        System.out.println(map.get("house.house"));
        System.out.println(map.get("house.user"));
    }

    public static Map<String,String> parseColumns2(String jsonStr){
        Map<String,String> result = new HashMap<String, String>();
        JSONObject json = JSONObject.parseObject(jsonStr);
        Iterator iterator = json.keySet().iterator();    //所有表信息
        String columns = "";
        List<String> keys = new ArrayList<String>();
        while(iterator.hasNext()){
            String hbase = iterator.next().toString();   //当前表
            //拼接hbase数据表的columns
            String key = "info:"+hbase;
            keys.add(key);
            String string = json.get(hbase).toString();  //字段信息

            //解析简单的字符串 {"name":"zhansan","type":"man"}
            JSONObject object = JSON.parseObject(string); //解析
            //拼接hive数据表的columns
            columns = columns + "," + object.get("name") + " " + object.get("type");
        }
        result.put("hive",columns);
        result.put("hbase",StringUtils.join(keys.toArray(), ","));
        return result;
    }

    public static Map<String,String> parseColumns(String jsonStr){
        Map<String,String> result = new HashMap<String, String>();
        JSONObject json = JSONObject.parseObject(jsonStr);
        Iterator iterator = json.keySet().iterator();    //所有表信息
        String columns = "";
        List<String> keys = new ArrayList<String>();
        while(iterator.hasNext()){
            String table = iterator.next().toString();  //当前表
            String string = json.get(table).toString();

            //解析简单的字符串 {"name":"zhansan","type":"man"}
            JSONObject object = JSON.parseObject(string);
            Iterator iterator2 = object.keySet().iterator();
            while(iterator2.hasNext()){
                String field = iterator.next().toString();  //当前字段
                //拼接hbase数据表的columns
                String key = "info:"+field;
                keys.add(key);
                columns = columns + "," + object.get(field);
            }
        }
        result.put("hive",columns);
        result.put("hbase",StringUtils.join(keys.toArray(), ","));
        return result;
    }

    /**
     * 复杂json字符串的解析
     */
    public static void testFastJson(){
        String json = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";
        JSONObject jsonObject = JSON.parseObject(json);
        //因为JSONObject继承了JSON，所以这样也是可以的
        //JSONObject jsonObject1 = JSONObject.parseObject(COMPLEX_JSON_STR);
        String string = jsonObject.getString("teacherName");
        Integer integer = jsonObject.getInteger("teacherAge");
        System.out.println("name:"+string+","+"age:"+integer);

        /**
         * 解析获得json字符串里面的json数据
         */
        JSONObject jsonObject1 = jsonObject.getJSONObject("course");
        System.out.println(jsonObject1.getString("courseName")+ ","+
            jsonObject1.getInteger("code"));

        /**
         * 遍历JsonArray
         */
        JSONArray students = jsonObject.getJSONArray("students");
        for (Object obj : students) {
            JSONObject jsonObject2 = (JSONObject) obj;
            System.out.println(jsonObject2.getString("studentName")+":"+jsonObject2.getInteger("studentAge"));
        }
    }

    public static void testFast(String string){
        List<TableInfo> tableList = JSONObject.parseArray(string,TableInfo.class);
        for(TableInfo tableInfo:tableList){
            System.out.println(tableInfo.getTablename()+"\t");
        }
    }

    /**
     * 将对象转化为json字符串
     * @param object
     * @return
     */
    public static String objectToJson(Object object){
        return JSON.toJSONString(object);
    }


    public static Map<String,Map<String,String>> parseColumnsTest(String jsonStr) {
        //将数据解析成这样的格式
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        //这里需要改了，将json字符串转换为一个List
        List<TableInfo> tableList = JSONObject.parseArray(jsonStr, TableInfo.class);
        for (TableInfo tableInfo : tableList) {
            Map<String, String> map = new HashMap<String, String>();
            List<String> keys = new ArrayList<String>();
            List<String> columnList = new ArrayList<String>();
            List<String> mysqlColumns = new ArrayList<String>();

            for (FieldInfo fieldInfo : tableInfo.getChildren()) {
                mysqlColumns.add(fieldInfo.getCOLUMN_NAME() + " " + fieldInfo.getCOLUMN_TYPE());
                if (!fieldInfo.getCOLUMN_KEY().equals("PRI")) {
                    columnList.add(fieldInfo.getCOLUMN_NAME() + " " + fieldInfo.getHIVE_TYPE());
                    //拼接hbase数据表的columns
                    String key = "info:" + fieldInfo.getCOLUMN_NAME();
                    keys.add(key);
                }
            }

            map.put("mysql", StringUtils.join(mysqlColumns.toArray(), ","));
            map.put("hive", StringUtils.join(columnList.toArray(), ","));
            map.put("hbase", StringUtils.join(keys.toArray(), ","));

            map.put("pri", tableInfo.getPri());
            result.put(tableInfo.getTablename(), map);
        }

        return result;
    }

//        JSONObject json = JSONObject.parseObject(jsonStr);
//        Iterator iterator = json.keySet().iterator();
//        while(iterator.hasNext()){     //获取所有表
//            Map<String,String> map = new HashMap<String, String>();
//            List<String> keys = new ArrayList<String>();
//            List<String> columnList = new ArrayList<String>();
//            List<String> mysqlColumns = new ArrayList<String>();
//            String pri = "";
//            String table = iterator.next().toString();     //当前表
//            System.out.println(table);
//            String string = json.get(table).toString();    //获取当前表的值
//            JSONObject object = JSON.parseObject(string);  //解析
//            //继续解析
//            Iterator iterator2 = object.keySet().iterator();
//            while(iterator2.hasNext()){
//                System.out.println("执行到内部");
//                String fieldName = iterator2.next().toString();  //当前字段
//                System.out.println(fieldName);
//
//                String string2 = object.get(fieldName).toString();    //获取当前字段的值
//                JSONObject object2 = JSON.parseObject(string2);     //解析
//
//                mysqlColumns.add(fieldName + " " + object2.get("HIVE_TYPE"));
//                if(!object2.get("COLUMN_KEY").equals("PRI")) {
//                    columnList.add(fieldName + " " + object2.get("HIVE_TYPE"));
//                    //拼接hbase数据表的columns
//                    String key = "info:"+fieldName;
//                    keys.add(key);
//                }
//
//                //获取主键信息
//                if(object2.get("COLUMN_KEY").equals("PRI")){
//                    pri = fieldName;
//                }
//            }
//            map.put("mysql",StringUtils.join(mysqlColumns.toArray(), ","));
//            map.put("hive",StringUtils.join(columnList.toArray(), ","));
//            map.put("hbase",StringUtils.join(keys.toArray(), ","));
//            map.put("pri",pri);
//            result.put(table,map);
//        }

//        return result;
//    }

    /**
     {
     "house.house": {
     "area": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "watch_times": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "create_time": {
     "HIVE_TYPE": "timestamp,string",
     "COLUMN_TYPE": "datetime",
     "COLUMN_KEY": ""
     },
     "city_en_name": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "total_floor": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "title": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "room": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "cover": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "last_update_time": {
     "HIVE_TYPE": "timestamp,string",
     "COLUMN_TYPE": "datetime",
     "COLUMN_KEY": ""
     },
     "region_en_name": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "price": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "build_year": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(4)",
     "COLUMN_KEY": ""
     },
     "street": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "district": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "admin_id": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "parlour": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "distance_to_subway": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "id": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": "PRI"
     },
     "floor": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "bathroom": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     },
     "status": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(4)",
     "COLUMN_KEY": ""
     },
     "direction": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": ""
     }
     },
     "house.user": {
     "last_update_time": {
     "HIVE_TYPE": "timestamp,string",
     "COLUMN_TYPE": "datetime",
     "COLUMN_KEY": ""
     },
     "password": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": ""
     },
     "create_time": {
     "HIVE_TYPE": "timestamp,string",
     "COLUMN_TYPE": "timestamp",
     "COLUMN_KEY": ""
     },
     "last_login_time": {
     "HIVE_TYPE": "timestamp,string",
     "COLUMN_TYPE": "datetime",
     "COLUMN_KEY": ""
     },
     "name": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": "UNI"
     },
     "phone_number": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(15)",
     "COLUMN_KEY": "UNI"
     },
     "id": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(11)",
     "COLUMN_KEY": "PRI"
     },
     "avatar": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(255)",
     "COLUMN_KEY": ""
     },
     "email": {
     "HIVE_TYPE": "string",
     "COLUMN_TYPE": "varchar(32)",
     "COLUMN_KEY": "UNI"
     },
     "status": {
     "HIVE_TYPE": "bigint,string,int,integer",
     "COLUMN_TYPE": "int(2)",
     "COLUMN_KEY": ""
     }
     }
     }
     */



}
