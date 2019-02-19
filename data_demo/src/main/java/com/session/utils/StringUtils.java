package com.session.utils;

public class StringUtils {
    /**
     * 获取指定字符串的值
     * param:str,累加器的值
     * param:key,所指定的字符串
     * return:String,返回key-value相对应的value
     */
    public static String getValueOfSessionOrPage(String str, String key){
        //通过"|"分割字符串
        String[] strs = str.split("\\|");
        //获取value 值
        String value = strs[MapUtils.getPosition(key)].split("=")[1];
        return value;
    }
    /**
     * 更新整个字符串的值
     * param:str1,累加器初始值
     * param:key,所指定的字符串
     * param:newValue,更新指定位置的值
     */
    public static String setValueOfSessionOrPage(String str1, String key, String newValue){
        //通过"|"分割字符串
        String[] strs = str1.split("\\|");
        //获取指定字符串的位置
        Integer position = MapUtils.getPosition(key);
        //设置新值
        String tmp = strs[position].split("=")[0]+"="+newValue.toString();
        strs[position] = tmp;
        //设置StringBuffer形成新的字符串
        StringBuffer buffer = new StringBuffer("");
        for(int i=0; i<strs.length;i++){
            //添加字符串，并在末尾添加"|"
            buffer.append(strs[i]);
            if(i<strs.length-1) {
                buffer.append("|");
            }
        }
        //返回修改后的字符串
        return buffer.toString();
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 判断字符串是否不为空
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 截断字符串两侧的逗号
     * @param str 字符串
     * @return 字符串
     */
    public static String trimComma(String str) {
        if(str.startsWith(",")) {
            str = str.substring(1);
        }
        if(str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 补全两位数字
     * @param str
     * @return
     */
    public static String fulfuill(String str) {
        if(str.length() == 2) {
            return str;
        } else {
            return "0" + str;
        }
    }

    /**
     * 从拼接的字符串中提取字段
     * @param str 字符串
     * @param delimiter 分隔符
     * @param field 字段
     * @return 字段值
     */
    public static String getFieldFromConcatString(String str,
                                                  String delimiter, String field) {
        try {
            String[] fields = str.split(delimiter);
            for(String concatField : fields) {
                // searchKeywords=|clickCategoryIds=1,2,3
                if(concatField.split("=").length == 2) {
                    String fieldName = concatField.split("=")[0];
                    String fieldValue = concatField.split("=")[1];
                    if(fieldName.equals(field)) {
                        return fieldValue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从拼接的字符串中给字段设置值
     * @param str 字符串
     * @param delimiter 分隔符
     * @param field 字段名
     * @param newFieldValue 新的field值
     * @return 字段值
     */
    public static String setFieldInConcatString(String str,
                                                String delimiter, String field, String newFieldValue) {
        String[] fields = str.split(delimiter);

        for(int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].split("=")[0];
            if(fieldName.equals(field)) {
                String concatField = fieldName + "=" + newFieldValue;
                fields[i] = concatField;
                break;
            }
        }

        StringBuffer buffer = new StringBuffer("");
        for(int i = 0; i < fields.length; i++) {
            buffer.append(fields[i]);
            if(i < fields.length - 1) {
                buffer.append("|");
            }
        }

        return buffer.toString();
    }


}