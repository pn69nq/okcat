package com.okcat.core;

import com.okcat.core.log.Log;
import com.okcat.core.okhttp.request.builder.BaseRequestBuilder;
import com.okcat.core.okhttp.client.factory.BaseOkHttpFactory;
import com.okcat.core.okhttp.response.adapter.BaseResultAdapter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * 提供了客户端管理工具
 *
 * 并提供对外的接口分装
 *
 * 包括 请求 (下载 上传功能未实现)
 *
 *
 *
 *
 *
 *
 */




public class OkCatPlugin {

    private BaseOkHttpFactory factory;
    private OkHttpClient globalClient;
    private boolean singleOkHttpClient = true;


    public OkCatPlugin(BaseOkHttpFactory factory) {
        this.factory = factory;
        globalClient = this.factory.getClient();
    }

    public OkCatPlugin(BaseOkHttpFactory factory, boolean singleOkHttpClient) {
        this.factory = factory;
        this.singleOkHttpClient = singleOkHttpClient;
        globalClient = this.factory.getClient();

    }

    public <T> void request(BaseRequestBuilder<Request> requestBuilder, OnResponseCallback callback, BaseResultAdapter adapter,Class<T> tClass) {
        Request request = requestBuilder.buildRequest();
        OkHttpClient client = globalClient;
        if (singleOkHttpClient) {
            client = factory.getClient();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onResponseFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(Log.getTag(OkCatPlugin.this), "onResponse");
                if (response.isSuccessful() && callback != null) {
                    byte[] result = response.body().bytes();
                    T t = adapter.adapter(result,tClass);
                    callback.onResponse(t);
                }

            }
        });
    }

    /**
     *
     * @param requestBuilder
     * @return
     */
    public <T> Observable rxRequest(BaseRequestBuilder<Request> requestBuilder, BaseResultAdapter adapter,Class<T> tClass) {
        Request request = requestBuilder.buildRequest();
        OkHttpClient client = globalClient;
        if (singleOkHttpClient) {
            client = factory.getClient();
        }

        Call call = client.newCall(request);
        Observable observable = new ObservableCreate(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                Response response = call.execute();
                byte[] result = response.body().bytes();
                Headers headers = response.headers();
                for (int i = 0, size = headers.size(); i < size; i++) {
                    Log.i("{=====}",headers.name(i)+"===>"+headers.value(i));
                }

                T t = adapter.adapter(result,tClass);
                emitter.onNext(t);
            }
        });
        observable.subscribeOn(Schedulers.io());
        return observable;
    }

    /**
     *
     * @param requestBuilder
     * @return
     */
    public <T> Observable rxRequestHaveBodyWithHeader(BaseRequestBuilder<Request> requestBuilder, BaseResultAdapter adapter,Class<T> tClass) {
        Request request = requestBuilder.buildRequest();
        OkHttpClient client = globalClient;
        if (singleOkHttpClient) {
            client = factory.getClient();
        }

        Call call = client.newCall(request);
        Observable observable = new ObservableCreate(new ObservableOnSubscribe<ResponseBodyWithHeader<T>>() {
            @Override
            public void subscribe(ObservableEmitter<ResponseBodyWithHeader<T>> emitter) throws Exception {
                Response response = call.execute();
                byte[] result = response.body().bytes();
                Headers headers = response.headers();
                Map<String,String> headerMap = new HashMap<>();
                for (int i = 0, size = headers.size(); i < size; i++) {
                    Log.i("{=====}",headers.name(i)+"===>"+headers.value(i));
                    headerMap.put(headers.name(i),headers.value(i));
                }

                T t = adapter.adapter(result,tClass);
                ResponseBodyWithHeader bodyWithHeader = new ResponseBodyWithHeader(t,headerMap);

                emitter.onNext(bodyWithHeader);
            }
        });
        observable.subscribeOn(Schedulers.io());
        return observable;
    }


    public void download() {

    }

    public void upload(List<File> fileList){

    }


    public interface OnResponseCallback<T> {
        void onResponse(T result);
        void onResponseFailure(Exception e);
    }

    public static class ResponseBodyWithHeader<T>{
        private T body;
        private Map<String,String> header;

        public ResponseBodyWithHeader(T body,Map<String,String> header){
            this.body = body;
            this.header = header;
        }

        public T getBody() {
            return body;
        }

        public Map<String, String> getHeader() {
            return header;
        }
    }

}
