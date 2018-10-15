package com.okcat.core.okhttp.request.builder;

import com.okcat.core.okhttp.request.builder.BaseOkHttpRequestBuilder;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.builder.request
 * @Description :  下载请求
 * @Creation Date:  2018-10-10 下午3:30
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */

public class DownloadRequestBuilder extends BaseOkHttpRequestBuilder {

    public DownloadRequestBuilder(String url){
        super(url);
    }

    @Override
    public Request buildRequest() {
        RequestBody body = createRequestFormBody(args);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }


}
