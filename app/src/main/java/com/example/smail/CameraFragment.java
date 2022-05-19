package com.example.smail;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;
import retrofit2.http.Url;
import android.app.Activity.*;

public class CameraFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carmeratab,container,false);


        Button bt_photo=view.findViewById(R.id.btn_photo);
        bt_photo.setOnClickListener(photoClick);



        return view;
    }
    View.OnClickListener photoClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            selectImage();

        }
    };

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);

    }

    public void onActivityResult(int requestCode, int resultCode,Intent intent){


        switch(requestCode){
            case 100:
                if(resultCode == RESULT_OK){
                    Uri fileUrl = intent.getData();
                    if(fileUrl != null){
                        uploadFile(fileUrl);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode,resultCode,intent);
    }

    private void uploadFile(Uri fileUri){
        KakaoClient kakaoClient = KakaoClient.getInstance();
        KakaoPhotoInterface kakaoPhotoInterface = KakaoClient.getRetrofitInterface();

        String filePath = "/sdcard/DCIM/Camera/20220422_192100.jpg";

        if(filePath !=null && !filePath.isEmpty()){
            File file = new File(filePath);
            if(file.exists()){
                RequestBody requestFile = RequestBody.create(MediaType.parse("form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);
                kakaoPhotoInterface.getPhotoFileResult(body).enqueue(new Callback<KakaoResult>(){
                    @Override
                    public void onResponse(Call<KakaoResult> call, Response<KakaoResult> response){
                        KakaoResult result = response.body();
                        List<KakaoPhotoResult> kakaoPhotoResult = result.getKakaoPhotoResult();

                        Log.d("kakao","Kata fetch success");
                    }
                    @Override
                    public void onFailure(Call<KakaoResult> call, Throwable t){
                        Log.d("retrofit",t.getMessage());
                    }
                });
            }
        }


    }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        }
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx); cursor.close();
        }
        System.out.print(filePath);
        return filePath;
    }



}