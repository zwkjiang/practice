package com.example.common;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit != null) {
            return retrofit;
        }
        synchronized (RetrofitManager.class) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://web.juhe.cn/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(createHttpClient())
                        .build();
            }
        }
        return retrofit;
    }

    private static OkHttpClient createHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("zhangweikai",message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private RetrofitManager(){}

    public void get() throws IOException {
        Executors.newCachedThreadPool();
        OkHttpClient httpClient = createHttpClient();
        Request build = new Request.Builder()
                .url("")
                .get()
                .header("", "")
                .build();// 引亢
        httpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        RequestBody requestBody = RequestBody.create(MediaType.get(""), "");
        Request build2 = new Request.Builder()
                .url("")
                .post(requestBody)
                .header("", "")
                .build();
        Response execute = httpClient.newCall(build2).execute();
    }
}
