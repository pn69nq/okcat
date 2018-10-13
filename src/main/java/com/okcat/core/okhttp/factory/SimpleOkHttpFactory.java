package com.okcat.core.okhttp.factory;

import com.okcat.core.okhttp.builder.config.OkHttpConfigBuilder;
import okhttp3.OkHttpClient;

public class SimpleOkHttpFactory extends BaseOkHttpFactory{

    private OkHttpConfigBuilder builder;

    public SimpleOkHttpFactory(OkHttpConfigBuilder builder){
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
