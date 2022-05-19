package com.example.smail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

public class KakaoPhotoResult {
    @SerializedName("boxes")
    @Expose
    private List<Number[]> boxes;

    @SerializedName("recognition_words")
    @Expose
    private String[] recognition_words;
}
