package com.okcat.core.okhttp.client.factory;

import com.okcat.core.okhttp.client.OkHttpClientConfigBuilder;
import okhttp3.OkHttpClient;

public class SimpleOkHttpFactory extends BaseOkHttpFactory{

    private OkHttpClientConfigBuilder builder;

    public SimpleOkHttpFactory(OkHttpClientConfigBuilder builder){
        this.builder = builder;
    }


    @Override
    public OkHttpClient getClient() {
        if(builder == null){
            return null;
        }
        return builder.build();
    }
}
