package com.okcat.core.okhttp.request.builder;

import com.okcat.core.log.Log;
import com.okcat.core.okhttp.request.builder.BaseOkHttpRequestBuilder;
import com.okcat.core.util.StringUtil;
import okhttp3.Headers;
import okhttp3.Request;

import java.util.Map;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.builder.request
 * @Description :  get 请求
 * @Creation Date:  2018-10-10 下午3:30
 * @ModificationHistory Who    When    What
 *
 * --------  ---------  --------------------------
 */

public class GetRequestBuilder extends BaseOkHttpRequestBuilder {

    public GetRequestBuilder(String url) {
        super(url);
    }


    @Override
    public Request buildRequest() {
        String newUrl = createUrl(url,args);
        Log.i(Log.getTag(this),newUrl);
        Headers headers = createHeaders(headerMap);
        Request request = new Request.Builder()
                .url(newUrl)
                .get()
                .build();
        if(headers != null){
            request = request.newBuilder().headers(headers).build();
        }
        return request;
    }

    public String createUrl(String url,Map<String,Object> args){
        if(!args.isEmpty()) {
            url = url + "?"+StringUtil.getUrlParamsByMap(args);
        }
        return url;
    }
}
