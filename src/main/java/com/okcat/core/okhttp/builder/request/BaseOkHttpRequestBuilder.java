package com.okcat.core.okhttp.builder.request;

import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.builder.request
 * @Description :  TODO
 * @Creation Date:  2018-10-10 下午5:17
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public abstract class BaseOkHttpRequestBuilder implements BaseRequestBuilder<Request> {


    protected Map<String, Object> args;
    protected Map<String,String> headerMap;
    protected String url;
    protected MediaType mediaType = MediaType.parse("application/json; charset=utf-8");


    public BaseOkHttpRequestBuilder(String url){
        this.url = url;
        args = new HashMap<>();
        headerMap = new HashMap<>();
    }


    public BaseOkHttpRequestBuilder setArgs(Map<String,Object> args){
        if(args != null && !args.isEmpty()){
            this.args.putAll(args);
        }
        return this;
    }

    public BaseOkHttpRequestBuilder setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    public BaseOkHttpRequestBuilder setHeader(String key, String value){
        this.headerMap.put(key,value);
        return this;
    }

    public BaseOkHttpRequestBuilder setArg(String key, Object value) {
        if (key == null) {
            throw new NullPointerException();
        }
        args.put(key, value);
        return this;
    }

    public BaseOkHttpRequestBuilder setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public Headers createHeaders(Map<String,String> headerMap){
        if(!headerMap.isEmpty()){
            Headers.Builder builder = new Headers.Builder();
            for(Map.Entry<String,String> entry:headerMap.entrySet()){
                String key = entry.getKey();
                if(key == null){
                    continue;
                }
                String value = entry.getValue();
                if(value == null){
                    builder.add(key);
                }
                else{
                    builder.add(key,value);
                }
            }
            return builder.build();
        }
        return null;
    }

    protected RequestBody createRequestFormBody(Map<String,Object> args){
        FormBody.Builder builder = new FormBody.Builder();
        if(!args.isEmpty()){
            for(Map.Entry<String,Object> entry:args.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                builder.add(key,String.valueOf(value));
            }
        }
        return builder.build();
    }

    protected RequestBody createBytesTypeRequestBody(byte[] args){
        return RequestBody.create(mediaType,args);
    }



}
