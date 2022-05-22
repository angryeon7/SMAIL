package com.example.smail;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import static android.os.Environment.DIRECTORY_PICTURES;
public class CameraFragment extends Fragment {
    View view;

    final private static String TAG = "GILBOMI";
    Button cameraButton;
    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;


    ImageView imageView;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carmeratab,container,false);

        Button bt_photo=view.findViewById(R.id.btn_photo);
        Button cameraButton=view.findViewById(R.id.cameraButton);
        bt_photo.setOnClickListener(photoClick);
        cameraButton.setOnClickListener(cameraClick);

        imageView = (ImageView) view.findViewById(R.id.iv_photo);

        textView = (TextView) view.findViewById(R.id.ocr_text);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료"); } else { Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        return view;
    }
    View.OnClickListener photoClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            selectImage();

        }
    };

    View.OnClickListener cameraClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cameraButton:
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE); break;
            }

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

                        imageView.setImageURI(fileUrl);

                    }
                }
                break;
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                } break;
        }
        super.onActivityResult(requestCode,resultCode,intent);
    }

    private void uploadFile(Uri fileUri){
        KakaoClient kakaoClient = KakaoClient.getInstance();
        KakaoPhotoInterface kakaoPhotoInterface = KakaoClient.getRetrofitInterface();


        String filePath = "/storage/emulated/0/DCIM/letter_1.jpg_resized.jpg";

        if(filePath !=null && !filePath.isEmpty()){
            File file = new File(filePath);
            if(file.exists()){
                RequestBody requestFile = RequestBody.create(MediaType.parse("form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);
                kakaoPhotoInterface.getPhotoFileResult(body).enqueue(new Callback<JsonObject>(){
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response){

                        System.out.println(response.body().toString());

                        Gson gson = new Gson();
                        recognizedWord RecognizeWord = gson.fromJson( response.body(),recognizedWord.class);
                        List<Result> word = RecognizeWord.getResult();
                        String textResult="";
                        String e;
                        for(int i = 0;i<word.size();i++){
                            List<String> eu = word.get(i).getRecognitionWords();
                            for(int j=0;j<eu.size();j++){
                                //결과
                                textResult+=eu.get(j)+"\n";
                            }

                        }
                        textView.setText(textResult);
                        System.out.print(textResult);

                        int i = textResult.indexOf("아");
                        System.out.println(i);

                        Log.d("kakao","Kata fetch success");
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t){
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