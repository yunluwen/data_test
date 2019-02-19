package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目配置logback
 */
public class LogBackTest {

    private final static Logger logger = LoggerFactory.getLogger(LogBackTest.class);

    public static void main(String[] args) {
        logger.info("logback 成功了");
        logger.error("logback 成功了");
        logger.debug("logback 成功了");
    }
}
