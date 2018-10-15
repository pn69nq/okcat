package com.okcat.core.okhttp.request.builder;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.builder.request
 * @Description :  TODO
 * @Creation Date:  2018-10-10 下午3:30
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */

public class PostRequestBuilder extends BaseOkHttpRequestBuilder {

    public static final int TYPE_STRING = 1;
    public static final int TYPE_BYTES = 2;

     private int type = TYPE_STRING;
    private byte[] bytes;

    public PostRequestBuilder(String url){
        super(url);
    }


    public PostRequestBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public PostRequestBuilder setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    @Override
    public Request buildRequest() {
        if(type == 1) {
            RequestBody body = createRequestFormBody(args);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            return request;
        }

        RequestBody body = createBytesTypeRequestBody(bytes);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;

    }
}
