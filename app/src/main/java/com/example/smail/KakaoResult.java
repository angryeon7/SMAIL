package com.example.smail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KakaoResult {
    @SerializedName("result")
    private List<KakaoPhotoResult> kakaoPhotoResult;

    public List<KakaoPhotoResult> getKakaoPhotoResult() {
        return kakaoPhotoResult;
    }
}

