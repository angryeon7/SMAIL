package com.example.smail;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.*;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface KakaoPhotoInterface {
    @Multipart
    @Headers({"Authorization: KakaoAK b6d7e6cd29a02e36eaa0089b4b3e85e1"})
    @POST("/v2/vision/text/ocr ")
    Call<JsonObject> getPhotoFileResult(@Part MultipartBody.Part file);

}
