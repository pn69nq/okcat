package com.okcat.core.okhttp.client.factory;

import okhttp3.OkHttpClient;

public abstract class BaseOkHttpFactory {
    public abstract OkHttpClient getClient();
}
