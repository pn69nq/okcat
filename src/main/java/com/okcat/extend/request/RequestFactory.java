package com.okcat.extend.request;

import com.okcat.core.okhttp.request.builder.BaseOkHttpRequestBuilder;
import com.okcat.core.okhttp.request.builder.GetRequestBuilder;
import com.okcat.core.okhttp.request.builder.PostRequestBuilder;
import com.okcat.core.okhttp.request.BaseRequestFactory;
import okhttp3.MediaType;

import java.util.Map;

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
    private Map<String,String> header;

    public RequestFactory(int requestType, int protocol, String url){
        this.requestType = requestType;
        this.url = url;
        this.protocol = protocol;
    }

    public RequestFactory(int requestType, int protocol, String url,Map<String,String> header){
        this.requestType = requestType;
        this.url = url;
        this.protocol = protocol;
        this.header = header;
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
