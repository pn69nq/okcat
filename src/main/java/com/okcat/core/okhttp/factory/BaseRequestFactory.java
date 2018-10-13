package com.okcat.core.okhttp.factory;

import com.okcat.core.okhttp.builder.request.BaseOkHttpRequestBuilder;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.extend.request
 * @Description :  TODO
 * @Creation Date:  2018-10-13 下午3:16
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public interface BaseRequestFactory {

    BaseOkHttpRequestBuilder getRequestBuilder();

}
