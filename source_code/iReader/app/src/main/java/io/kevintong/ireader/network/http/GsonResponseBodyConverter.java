package io.kevintong.ireader.network.http;


import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import io.kevintong.ireader.network.entity.HttpResult;
import okhttp3.ResponseBody;
import retrofit2.Converter;


class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final static String TAG = GsonResponseBodyConverter.class.getSimpleName();

    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d(TAG,  " -- Network response>>" + response);
        //httpResult 只解析result字段
        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
        Log.d(TAG,  " -- Network httpResult>>" + httpResult);
        return gson.fromJson(response, type);


    }
}
