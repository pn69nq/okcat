package com.okcat.core.okhttp.builder.request;


/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.builder.request
 * @Description :  创建请求接口builder接口类
 * @Creation Date:  2018-10-10 下午3:30
 * @ModificationHistory Who    When    What
 * <p>
 * --------  ---------  --------------------------
 */

public interface BaseRequestBuilder<T> {
    T buildRequest();
}
