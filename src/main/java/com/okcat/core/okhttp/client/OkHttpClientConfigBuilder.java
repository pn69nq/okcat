package com.okcat.core.okhttp.client;

import com.okcat.core.okhttp.client.interceptor.OkHttpLogger;
import com.okcat.core.okhttp.client.ssl.SSLParams;
import com.okcat.core.okhttp.client.ssl.SSLUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class OkHttpClientConfigBuilder {

    protected String url;


    //请求超时时间
    protected long readTimeout;
    protected long connectTimeout;
    protected SSLParams sslParams;

    protected HttpLoggingInterceptor.Logger logger;


    public OkHttpClientConfigBuilder setUrl(String url){
        this.url = url;
        return this;
    }

    public OkHttpClientConfigBuilder setLogger(HttpLoggingInterceptor.Logger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * 信任所有证书,不安全有风险
     *
     * @return
     */
    public OkHttpClientConfigBuilder sslSocketFactory() {
        sslParams = SSLUtils.getSslSocketFactory();
        return this;
    }

    /**
     * 使用预埋证书，校验服务端证书（自签名证书）
     *
     * @param certificates
     * @return
     */
    public OkHttpClientConfigBuilder sslSocketFactory(InputStream... certificates) {
        sslParams = SSLUtils.getSslSocketFactory(certificates);
        return this;
    }

    /**
     * 使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
     *
     * @param bksFile
     * @param password
     * @param certificates
     * @return
     */
    public OkHttpClientConfigBuilder sslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
        sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
        return this;
    }

    public OkHttpClient build() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if(readTimeout > 0){
            okHttpClientBuilder.readTimeout(readTimeout,TimeUnit.SECONDS);
        }
        if(connectTimeout > 0){
            okHttpClientBuilder.connectTimeout(connectTimeout,TimeUnit.SECONDS);
        }
        if(sslParams != null){
            okHttpClientBuilder.sslSocketFactory(sslParams.getSSLSocketFactory(),sslParams.getTrustManager());
        }
        if(logger != null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new OkHttpLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return okHttpClientBuilder.build();
    }



}
