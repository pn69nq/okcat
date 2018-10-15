package com.okcat;

import com.okcat.core.OkCatPlugin;
import com.okcat.core.okhttp.client.OkHttpClientConfigBuilder;
import com.okcat.core.okhttp.request.builder.BaseOkHttpRequestBuilder;
import com.okcat.core.okhttp.request.builder.GetRequestBuilder;
import com.okcat.core.okhttp.request.builder.PostRequestBuilder;
import com.okcat.core.okhttp.client.factory.SimpleOkHttpFactory;
import com.okcat.core.okhttp.client.interceptor.OkHttpLogger;
import com.okcat.core.okhttp.response.adapter.BaseResultAdapter;
import com.okcat.extend.request.RequestConfig;
import com.okcat.extend.request.RequestFactory;
import com.okcat.extend.response.adapter.DefaultResultAdapter;
import com.okcat.extend.response.adapter.JsonResultAdapter;
import com.okcat.extend.response.adapter.ProtoResultAdapter;
import io.reactivex.Observable;

import java.util.Map;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat
 * @Description :  TODO
 * @Creation Date:  2018-10-13 下午4:09
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class OkCat {

    private static OkCat miu;
    private OkCatPlugin plugin;

    private OkCat() {
        OkHttpClientConfigBuilder builder = new OkHttpClientConfigBuilder();
        builder.setLogger(new OkHttpLogger());
        plugin = new OkCatPlugin(new SimpleOkHttpFactory(builder));
    }

    public static OkCat getMiu() {
        if (miu == null) {
            synchronized (OkCat.class) {
                if (miu == null) {
                    miu = new OkCat();
                }
            }
        }
        return miu;
    }


    /***
     * 使用pb 传递参数请求,接收pb response
     * pb 请求
     * @param url
     * @param args
     * @param tClass 需要返回的对象的class对象
     * @param callback
     * @param <T>
     */
    public <T> void simplePostRequestPb(String url, byte[] args, Class<T> tClass,OkCatPlugin.OnResponseCallback callback) {
        RequestFactory factory = new RequestFactory(RequestConfig.REQUEST_METHOD_POST,RequestConfig.PROTOCOL_TYPE_PROTO,url);
        PostRequestBuilder builder = (PostRequestBuilder) factory.getRequestBuilder();
        builder.setBytes(args);
        BaseResultAdapter adapter = new ProtoResultAdapter();
        plugin.request(builder,callback,adapter,tClass);
    }

    /**
     * 使用pb 传递参数请求,接收pb response
     * rxjava2 pb请求
     * @param url
     * @param args
     * @param tClass 需要返回的对象的class对象
     * @param <T>
     */
    public <T> Observable simplePostRequestRxPb(String url, byte[] args, Class<T> tClass) {
        RequestFactory factory = new RequestFactory(RequestConfig.REQUEST_METHOD_POST,RequestConfig.PROTOCOL_TYPE_PROTO,url);
        PostRequestBuilder builder = (PostRequestBuilder) factory.getRequestBuilder();
        builder.setBytes(args);
        BaseResultAdapter adapter = new ProtoResultAdapter();
        return plugin.rxRequest(builder,adapter,tClass);
    }



    /***
     * 使用pb 传递参数请求,接收pb response
     * pb 请求
     * @param url
     * @param args
     * @param tClass 需要返回的对象的class对象
     * @param callback
     * @param <T>
     */
    public <T> void simpleGetRequestPb(String url, Map<String,Object> args, Class<T> tClass,OkCatPlugin.OnResponseCallback callback) {
        RequestFactory factory = new RequestFactory(RequestConfig.REQUEST_METHOD_GET,RequestConfig.PROTOCOL_TYPE_PROTO,url);
        GetRequestBuilder builder = (GetRequestBuilder) factory.getRequestBuilder();
        builder.setArgs(args);
        BaseResultAdapter adapter = new ProtoResultAdapter();
        plugin.request(builder,callback,adapter,tClass);
    }

    /**
     * rxjava2 pb请求
     * @param url
     * @param args
     * @param tClass 需要返回的对象的class对象
     * @param <T>
     */
    public <T> Observable simpleGetRequestRxPb(String url, Map<String,Object> args, Class<T> tClass) {
        RequestFactory factory = new RequestFactory(RequestConfig.REQUEST_METHOD_GET,RequestConfig.PROTOCOL_TYPE_PROTO,url);
        GetRequestBuilder builder = (GetRequestBuilder) factory.getRequestBuilder();
        builder.setArgs(args);
        BaseResultAdapter adapter = new ProtoResultAdapter();
        return plugin.rxRequest(builder,adapter,tClass);
    }



    /**
     * json
     * @param requestMethod
     * @param url
     * @param args
     * @param tClass 需要返回的对象的class对象
     * @param callback
     * @param <T>
     */
    public <T> void simpleRequest(int requestMethod,int protocol,String url,Map<String,Object> args,Class<T> tClass,OkCatPlugin.OnResponseCallback callback){
        if(requestMethod != RequestConfig.REQUEST_METHOD_GET && requestMethod != RequestConfig.REQUEST_METHOD_POST ){
            return;
        }
        RequestFactory factory = new RequestFactory(requestMethod,protocol,url);
        BaseOkHttpRequestBuilder builder = factory.getRequestBuilder();
        builder.setArgs(args);
        plugin.request(builder,callback,getResultAdapter(protocol),tClass);

    }

    /**
     * rxjava2 json
     * @param requestMethod
     * @param url
     * @param args
     * @param tClass 需要返回的对象的class对象
     * @param <T>
     */
    public <T> Observable simpleRequestRx(int requestMethod,int protocol, String url, Map<String, Object> args, Class<T> tClass) {
        if (requestMethod != RequestConfig.REQUEST_METHOD_GET && requestMethod != RequestConfig.REQUEST_METHOD_POST) {
            throw new IllegalArgumentException();
        }
        RequestFactory factory = new RequestFactory(requestMethod,protocol, url);
        BaseOkHttpRequestBuilder builder = factory.getRequestBuilder();
        builder.setArgs(args);
        return plugin.rxRequest(builder, getResultAdapter(protocol), tClass);

    }

    private BaseResultAdapter getResultAdapter(int protocol){
        BaseResultAdapter adapter = null;
        if(protocol == RequestConfig.PROTOCOL_TYPE_JSON){
            adapter = new JsonResultAdapter();
        }
        else if(protocol == RequestConfig.PROTOCOL_TYPE_PROTO){
            adapter = new ProtoResultAdapter();
        }
        else {
            adapter = new DefaultResultAdapter<byte[]>();
        }
        return adapter;
    }
}
