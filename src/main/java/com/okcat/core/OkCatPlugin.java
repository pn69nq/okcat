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

import java.io.IOException;

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
                T t = adapter.adapter(result,tClass);
                emitter.onNext(t);
            }
        });
        observable.subscribeOn(Schedulers.io());
        return observable;
    }


    public void download() {

    }


    public interface OnResponseCallback<T> {
        void onResponse(T result);
        void onResponseFailure(Exception e);
    }

}
