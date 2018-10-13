package com.okcat.core.okhttp.response.adapter;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.response.adapter
 * @Description :  结果转换适配
 * @Creation Date:  2018-10-11 下午2:09
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */

public interface BaseResultAdapter {

    <T,R> T adapter(R result, Class<T> tClass);
}
