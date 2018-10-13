package com.okcat.core;

import com.okcat.OkCat;
import com.okcat.core.log.Log;
import com.okcat.core.okhttp.builder.request.GetRequestBuilder;
import com.okcat.extend.request.RequestConfig;
import com.okcat.extend.request.RequestFactory;
import com.pq.user.protobuf.ProtoStudent;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class OkCatPluginTest {


    @Test
    public void get() {
//        try {
//            GetRequestBuilder builder = new GetRequestBuilder("http://localhost:8098/index");
//            builder.setMediaType(MediaType.get("application/json; charset=utf-8"));
//            builder.setArg("test","gooo");
//            plugin.get(builder);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            OkCat okCat = OkCat.getMiu();
            okCat.simpleGetRequestPb("http://localhost:8098/get",null,ProtoStudent.Student.class, new OkCatPlugin.OnResponseCallback<ProtoStudent.Student>() {
                @Override
                public void onResponse(ProtoStudent.Student student) {

//                        ProtoStudent.Student student = ProtoStudent.Student.parseFrom(result);
                    Log.i(Log.getTag(OkCatPluginTest.this), student.getEmail());
                    Log.i(Log.getTag(OkCatPluginTest.this), "id" + student.getId());

                }

                @Override
                public void onResponseFailure(Exception e) {

                }
            });

            Thread.sleep(5000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void rxPost() throws IOException {
        OkCat okCat = OkCat.getMiu();
        ProtoStudent.Student student = ProtoStudent.Student.newBuilder().setId(110).setEmail("高哦哦嘎嘎").build();
        Observable observable = okCat.simplePostRequestRxPb("http://localhost:8098/post2",student.toByteArray(), ProtoStudent.Student.class);
        observable.subscribe(new Consumer<ProtoStudent.Student>() {
            @Override
            public void accept(ProtoStudent.Student result) throws Exception {
//                    ProtoStudent.Student student = ProtoStudent.Student.parseFrom(result);
                Log.i(Log.getTag(OkCatPluginTest.this), " " + result.getEmail());
                Log.i(Log.getTag(OkCatPluginTest.this), "id " + result.getId());

            }
        });
    }

    @Test
    public void rxGet() throws IOException {
        OkCat okCat = OkCat.getMiu();
        Map<String,Object> args = new HashMap<>();
        args.put("test", "gooo");


        Observable observable = okCat.simpleRequestRx(RequestConfig.REQUEST_METHOD_GET,RequestConfig.PROTOCOL_TYPE_DEFAULT,"http://localhost:8098/index",args,null);
        observable.subscribe(new Consumer<byte[]>() {
            @Override
            public void accept(byte[] result) throws Exception {
                Log.i(Log.getTag(OkCatPluginTest.this), new String(result));
            }
        });


    }
}