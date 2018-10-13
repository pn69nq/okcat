package com.okcat.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.log
 * @Description :  日志
 * @Creation Date:  2018-10-10 下午4:01
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class Log {

    static Logger LOGGER = LoggerFactory.getLogger(Log.class);

    public static void i(String tag,String log){
        LOGGER.info(tag+"{} ", log);
    }
    public static void e(String tag,String log){
        LOGGER.error(tag+"{} ", log);
    }

    public static void d(String tag,String log){
        LOGGER.debug(tag+"{} ", log);
    }

    public static String getTag(Object o){
        return o.getClass().getSimpleName();
    }


}
