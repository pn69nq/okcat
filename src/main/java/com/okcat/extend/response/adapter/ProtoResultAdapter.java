package com.okcat.extend.response.adapter;

import com.google.protobuf.GeneratedMessageV3;
import com.okcat.core.okhttp.response.adapter.BaseResultAdapter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author :  test
 * @Project Name :  okcat
 * @Package Name :  com.okcat.core.okhttp.response.adapter
 * @Description :  TODO
 * @Creation Date:  2018-10-11 下午2:09
 * @ModificationHistory Who    When    What
 * --------  ---------  --------------------------
 */
public class ProtoResultAdapter<T extends GeneratedMessageV3> implements BaseResultAdapter {

    @Override
    public <T, R> T adapter(R result, Class<T> tClass) {
        try {
            Method parseMethod = tClass.getDeclaredMethod("parseFrom", InputStream.class);
            T obj = (T) parseMethod.invoke(tClass, new ByteArrayInputStream((byte[])result));
            return obj;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
