package com.okcat.core.okhttp.response;

import com.okcat.core.okhttp.response.adapter.BaseResultAdapter;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.response
 * @Description :  TODO
 * @Creation Date:  2018-10-13 下午2:01
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public abstract class ResponseAdapterFactory<T> {

    private BaseResultAdapter adapter;

    public abstract T get();


}
