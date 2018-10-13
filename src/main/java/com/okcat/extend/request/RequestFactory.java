package com.okcat.extend.request;

import com.okcat.core.okhttp.builder.request.BaseOkHttpRequestBuilder;
import com.okcat.core.okhttp.builder.request.GetRequestBuilder;
import com.okcat.core.okhttp.builder.request.PostRequestBuilder;
import com.okcat.core.okhttp.factory.BaseRequestFactory;
import okhttp3.MediaType;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.extend.request
 * @Description :  TODO
 * @Creation Date:  2018-10-13 下午3:16
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class RequestFactory implements BaseRequestFactory {

    private int requestType = RequestConfig.REQUEST_METHOD_GET;
    private int protocol = RequestConfig.PROTOCOL_TYPE_DEFAULT;
    private String url;

    public RequestFactory(int requestType, int protocol, String url){
        this.requestType = requestType;
        this.url = url;
        this.protocol = protocol;
    }

    @Override
    public BaseOkHttpRequestBuilder getRequestBuilder() {
        BaseOkHttpRequestBuilder builder = null;
        if(requestType == RequestConfig.REQUEST_METHOD_GET){
            builder = new GetRequestBuilder(url);
        }
        else{
            builder = new PostRequestBuilder(url);
        }
        if(protocol == RequestConfig.PROTOCOL_TYPE_JSON){
            builder.setMediaType(MediaType.get("application/json; charset=utf-8"));
        }
        else if(protocol == RequestConfig.PROTOCOL_TYPE_PROTO){
            builder.setHeader("Accept", "application/x-protobuf").
                    setMediaType(MediaType.get("application/x-protobuf;charset=utf-8"));
            if(requestType == RequestConfig.REQUEST_METHOD_POST){
                ((PostRequestBuilder)builder).setType(PostRequestBuilder.TYPE_BYTES);
            }
        }
        return builder;
    }
}
