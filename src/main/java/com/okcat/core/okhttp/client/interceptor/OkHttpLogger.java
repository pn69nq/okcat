package com.okcat.core.okhttp.client.interceptor;

import com.okcat.core.log.Log;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.interceptor
 * @Description :  TODO
 * @Creation Date:  2018-10-11 下午5:16
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class OkHttpLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        Log.i(Log.getTag(this),message);
    }
}
