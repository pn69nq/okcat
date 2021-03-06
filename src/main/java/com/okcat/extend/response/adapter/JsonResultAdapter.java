package com.okcat.extend.response.adapter;

import com.alibaba.fastjson.JSON;
import com.okcat.core.okhttp.response.adapter.BaseResultAdapter;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.response.adapter
 * @Description :  TODO
 * @Creation Date:  2018-10-11 下午2:09
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class JsonResultAdapter<R extends String> implements BaseResultAdapter {


    @Override
    public <T, R> T adapter(R result, Class<T> tClass) {
        return JSON.parseObject((byte[]) result,tClass);
    }
}