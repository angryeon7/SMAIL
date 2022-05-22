package com.example.smail;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoClient {
    private static KakaoClient instance = null;
    private static KakaoPhotoInterface kakaoPhotoInterface;
    private static String baseUrl = "https://dapi.kakao.com";

    private KakaoClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kakaoPhotoInterface = retrofit.create(KakaoPhotoInterface.class);

    }

    public static KakaoClient getInstance(){
        if(instance == null){
            instance = new KakaoClient();
        }
        return instance;
    }

    public static KakaoPhotoInterface getRetrofitInterface(){
        return kakaoPhotoInterface;
    }
}
