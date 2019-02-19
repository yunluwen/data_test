//package com.session.utils;
//
//// import org.apache.commons.logging.Log;
//// import org.apache.commons.logging.LogFactory;
////import org.apache.log4j.Logger;
//// import org.apache.log4j.PropertyConfigurator;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//
///**
// * 记录日志的工具类 主要输出字符串 </br>
// *
// * @author zhangyunhao
// */
//public class LogUtils {
//    // log4j配置文件
//    // private static final String PROPERTIES_FILE_NAME = "log4j2.properties";
//    private static LogUtils instance = null;
//    private static Logger logger = null;
//    // 利用JCL(commons-logging)组件
//    // private static Log jclLogger = null;
//
//    static {
//        // PropertyConfigurator.configure(PROPERTIES_FILE_NAME);
//        // logger = Logger.getRootLogger();
//        // jclLogger = LogFactory.getLog(Log4jUtils.class); // 创建JCL的Log实例
//
//        //加载log4j配置
////        ConfigurationSource source;
////        String path="log4j2.xml";
////        URL url=Log4jUtils.class.getClassLoader().getResource(path);
////        System.out.println(url);
////        try {
////            source = new ConfigurationSource(new FileInputStream(new File(url.getPath())),url);
////            Configurator.initialize(null, source);
//        logger = LogManager.getLogger(LogUtils.class.getName());
////        }catch (Exception e){
////
////        }
//
////        String relativelyPath=System.getProperty("user.dir");
////        System.setProperty("log.base",relativelyPath);
////        logger = Logger.getLogger(Log4jUtils.class);
//
//    }
//
//    private LogUtils() {
//    }
//
//    public static LogUtils getInstance() {
//        synchronized (LogUtils.class) {
//            if (instance == null) {
//                instance = new LogUtils();
//            }
//        }
//        return instance;
//    }
//
//    public static void setLoggerPath() {
//        String relativelyPath=System.getProperty("user.dir");
//        //日志输出到上一级目录
////        File curAllInOneProjectFile = new File(relativelyPath);
////        File rootProjectFileDir = curAllInOneProjectFile.getParentFile();
////        System.setProperty("log.base",rootProjectFileDir.toString());
//        System.setProperty("log.base",relativelyPath);
//    }
//
//    public static void debug(String str) {
//        logger.debug(str);
//        // jclLogger.debug(str);
//    }
//
//    public static void debug(String str, Exception e) {
//        logger.debug(str, e);
//    }
//
//    public static void info(String str) {
//        logger.info(str);
//        // jclLogger.info(str);
//    }
//
//    public static void info(String str, Exception e) {
//        logger.info(str, e);
//    }
//
//    public static void warn(String str) {
//        logger.warn(str);
//        // jclLogger.warn(str);
//    }
//
//    public static void warn(String str, Exception e) {
//        logger.warn(str, e);
//    }
//
//    public static void error(String str) {
//        logger.error(str);
//        // jclLogger.error(str);
//    }
//
//    public static void error(String str, Exception e) {
//        logger.error(str, e);
//    }
//
//    public static void fatal(String str) {
//        logger.fatal(str);
//        // jclLogger.fatal(str);
//    }
//
//    public static void fatal(String str, Exception e) {
//        logger.fatal(str, e);
//    }
//}
//
