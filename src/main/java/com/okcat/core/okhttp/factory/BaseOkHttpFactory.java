package com.okcat.core.okhttp.factory;

import okhttp3.OkHttpClient;

public abstract class BaseOkHttpFactory {
    public abstract OkHttpClient getClient();
}
