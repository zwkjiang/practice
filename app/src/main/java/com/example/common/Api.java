package com.example.common;


import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    @Headers({"Device:Android","Level:1"})
    @GET("constellation/getAll")
    Observable<Object> getData(@Query("city") String name);
}
