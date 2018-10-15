package com.okcat.core.okhttp.request;

import com.okcat.core.okhttp.request.builder.BaseOkHttpRequestBuilder;

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
