package com.okcat.core.okhttp.ssl;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class SSLParams {
    private SSLSocketFactory sSLSocketFactory;
    private X509TrustManager trustManager;

    public SSLSocketFactory getSSLSocketFactory() {
        return sSLSocketFactory;
    }

    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sSLSocketFactory = sSLSocketFactory;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public void setTrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
    }
}