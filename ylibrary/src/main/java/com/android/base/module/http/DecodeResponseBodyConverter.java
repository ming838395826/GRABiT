package com.android.base.module.http;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/12/14.
 */

public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //解密字符串
        String response=value.string();
        String strResult = response.substring(0, response.length());
        Logger.e(strResult);

        JsonObject returnData = new JsonParser().parse(strResult).getAsJsonObject();
        String status = returnData.get("code").getAsString();
        if("1".equals(status)){
//            return adapter.fromJson(strResult);
        }else{
            //异常，请求失败
            strResult = strResult.replace("\"data\":[]","\"data1\":[]");
            return adapter.fromJson(strResult);
        }
        return adapter.fromJson(strResult);

        //解密字符串
//        String response = value.string();
//        String strResult = response.substring(0, response.length());
//        if(strResult == null){
//            return null;
//        }
//
//        if(!strResult.startsWith("{")){
//            strResult = "{\"data\":\""+new String(response.getBytes("UTF-8"))+"\"}";
//        }
//        Log.d("converter", "解密的服务器数据：" + strResult);
//        return adapter.fromJson(strResult);
    }
}

class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        //加密
        Logger.e(JSONObject.toJSONString(value));
//        APIBodyData data = new APIBodyData();
//        data.setData(XXTEA.Encrypt(value.toString(), HttpConstant.KEY));
//        String postBody = gson.toJson(data); //对象转化成json
//        Log.i("xiaozhang", "转化后的数据：" + postBody);
//        return RequestBody.create(MEDIA_TYPE, postBody);
//        ###这里需要，特别注意的是，request是将T转换成json数据。你要在T转换成json之后再做加密。再将数据post给服务器，同时要注意，你的T到底指的那个对象
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.flush();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
