package com.session.constants;

public interface Constants {

    //关于spark配置
    String SPARK_APP_NAME= "SessionAnalyze";
    String SPARK_MASTER = "local";
    String SPARK_LOCAL = "spark.local";
    String SPARK_LOCAL_SESSION_TASKID = "spark.local.taskid.session";


    String JDBC_URL = "jdbc.url";
    String JDBC_USER = "jdbc.user";
    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_PASSWORD = "jdbc.password";

    String JDBC_USER_PROD = "jdbc.prod.user";
    String JDBC_URL_PROD = "jdbc.prod.url";
    String JDBC_PASSWORD_PROD = "jdbc.prod.password";

    String MYSQL_HOST = "mysql_host";
    String MYSQL_PORT = "mysql_port";

    String DBC_DATASOURCE_SIZE = "jdbc.datasource.size";


    //年月日的时间格式:yyyy-MM-dd
    String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    //具体到分秒的时间格式:yyyy-MM-dd HH:mm:ss
    String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";


    /**
     * 设置SQL查询条件常量
     */
    //设置用户id
    String USER_ID = "userid";
    //设置用户年龄
    String USER_AGE = "age";
    //设置用户职业
    String USER_PROFESSIONAL = "professional";
    //设置用户性别
    String USER_SEX = "sex";
    //设置用户所在城市
    String USER_CITY = "city";
    //设置session 商品点击分类
    String SESSION_CLICKCATERORY = "clickcaterory";
    //设置session 搜索关键词
    String SESSION_SEARCHWORD = "searchword";
    //设置session id
    String SESSION_ID = "sessionid";
    //设置session 商品下单分类
    String SESSION_ORDERCATERORY = "ordercaterory";
    //设置session 商品支付分类
    String SESSION_PAYCATERORY = "paycaterory";
    //设置session 商品分类
    String SESSION_CATERORY = "caterory";

    // 分割符
    String VALUE_SEPARATOR = "|";

    /**
     * 聚合统计业务计算相关变量
     */
    String SESSION_COUNT = "session_count";

    String TIME_PERIOD_1s_3s = "1s_3s";
    String TIME_PERIOD_4s_6s = "4s_6s";
    String TIME_PERIOD_7s_9s = "7s_9s";
    String TIME_PERIOD_10s_30s = "10s_30s";
    String TIME_PERIOD_30s_60s = "30s_60s";
    String TIME_PERIOD_1m_3m = "1m_3m";
    String TIME_PERIOD_3m_10m = "3m_10m";
    String TIME_PERIOD_10m_30m = "10m_30m";
    String TIME_PERIOD_30m = "30m";

    String STEP_PERIOD_1_3 = "1_3";
    String STEP_PERIOD_4_6 = "4_6";
    String STEP_PERIOD_7_9 = "7_9";
    String STEP_PERIOD_10_30 = "10_30";
    String STEP_PERIOD_30_60 = "30_60";
    String STEP_PERIOD_60 = "60";

    String profex = "hdfs://hdp-node-01:9000/";
    //数据存放地址，用户名
    String USER_PATH = profex+"data/user_info.txt";
    //数据存放地址，商品
    String PRODUCT_PATH = profex+"/data/product_info.txt";
    //数据存放地址，点击行为日志
    String CLICK_LOG_PATH = profex+"data/click.txt";
    //常量，数据存放地址，session粒度聚合后，数据存放地址
    // String SESSION_POLYMERIZATION_STORE_PATH = "hdfs://hdp-node-01:9000/poly/";
    //常量，用户查询数据保存在HDFS地址，此处为保存在本地地址
    // String USER_QUERY_STORE_PATH = "hdfs://hdp-node-01:9000/query/";
    //读取目标文件，即hdfs上存放的part-*文件
    //public static final String PART_FILE = "part-*";

    /**
     * 任务开始日期，任务结束日期
     */
    String PARAM_START_DATE = "startDate";
    String PARAM_END_DATE = "endDate";
    String PARAM_START_AGE = "startAge";
    String PARAM_END_AGE = "endAge";
    String PARAM_PROFESSIONALS = "professionals";
    String PARAM_CITIES = "cities";
    String PARAM_SEX = "sex";
    String PARAM_KEYWORDS = "keywords";
    String PARAM_CATEGORY_IDS = "categoryIds";
    String PARAM_TARGET_PAGE_FLOW = "targetPageFlow";


}