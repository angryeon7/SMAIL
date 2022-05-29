package com.example.smail;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Session2Command;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStorageDirectory;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.OpenCVLoader;

public class CameraFragment extends Fragment {
    View view;

    final private static String TAG = "GILBOMI";
    Button cameraButton;
    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    ImageView imageView;//선택된 사진이 들어갈 뷰
    EditText textView;// 인식된 본문
    EditText today_date;//오늘 날짜
    EditText writer_;//writer 인식하고 default 발신자
    Uri uri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carmeratab,container,false);

        OpenCVLoader openCVLoader;
        Button bt_photo=view.findViewById(R.id.btn_photo);
        Button cameraButton=view.findViewById(R.id.cameraButton);
        bt_photo.setOnClickListener(photoClick);
        cameraButton.setOnClickListener(cameraClick);

        imageView = (ImageView) view.findViewById(R.id.iv_photo);

        textView = (EditText) view.findViewById(R.id.ocr_text);
        today_date = (EditText) view.findViewById(R.id.today_date);
        writer_ = (EditText) view.findViewById(R.id.writer);

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
    View.OnClickListener photoClick = new View.OnClickListener(){//갤러리 사진 불러오기 버튼
        @Override
        public void onClick(View view) {
            selectImage();

        }
    };

    View.OnClickListener cameraClick = new View.OnClickListener(){//카메라 연결 버튼
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cameraButton:
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        File photoFIle = createImageFile();//파일 저장하기 함수 호출
                        uri = Uri.fromFile(photoFIle);//저장한 파일에서 uri 가져오기

                    } catch (IOException e) {

                    }
                    startActivityForResult(cameraIntent, TAKE_PICTURE); break;
            }

        }
    };


    private void selectImage(){//갤러리 불러오기
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);

    }

    public void onActivityResult(int requestCode, int resultCode,Intent intent){

        switch(requestCode){
            case 100://갤러리
                if(resultCode == RESULT_OK){
                    Uri fileUrl = intent.getData();
                    if(fileUrl != null){
                        uploadFile(fileUrl);

                        imageView.setImageURI(fileUrl);

                    }
                }
                break;
            case TAKE_PICTURE://카메라
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    Uri fileUrl = getImageUri(getActivity(),bitmap);
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);

                        uploadFile(fileUrl);

                    }
                } break;
        }
        super.onActivityResult(requestCode,resultCode,intent);
    }

    private void uploadFile(Uri fileUri){//kakao api 불러오는 함수
        KakaoClient kakaoClient = KakaoClient.getInstance();
        KakaoPhotoInterface kakaoPhotoInterface = KakaoClient.getRetrofitInterface();


        System.out.println(fileUri);
        System.out.println(fileUri.getPath());
        String filePath = getRealPathFromURI(getActivity(),fileUri);//uri에서 realfilepath 가져오기

        System.out.println(filePath);
        if(filePath !=null && !filePath.isEmpty()){
            File file = new File(filePath);
            //SaveBitmapToFileCache(erode_cv(file),filePath);
            //전처리 함수

            System.out.println(file.exists()+"file.exists");

            if(file.exists()){
                RequestBody requestFile = RequestBody.create(MediaType.parse("form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);
                kakaoPhotoInterface.getPhotoFileResult(body).enqueue(new Callback<JsonObject>(){
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response){//kakao api 호출 받은 값 처리

                        //System.out.println(response.body().toString());

                        try {
                            Gson gson = new Gson();
                            recognizedWord RecognizeWord = gson.fromJson(response.body(), recognizedWord.class);
                            List<Result> word = RecognizeWord.getResult();
                            String textResult = "";
                            String writer;
                            String e;
                            for (int i = 0; i < word.size(); i++) {
                                List<String> eu = word.get(i).getRecognitionWords();
                                for (int j = 0; j < eu.size(); j++) {
                                    //결과
                                    textResult += eu.get(j);
                                }

                            }
                            if(textResult==""){
                                textView.setText("인식불가");
                            }else{
                            textView.setText(textResult);
                            System.out.print(textResult);
                            }

                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일");
                            Date date = new Date();
                            String date_ = format1.format(date);
                            today_date.setText(date_);
                            if (textResult.contains(("씀")) == true) {
                                int j = textResult.indexOf("씀");
                                writer = textResult.substring(j - 4, j - 1);
                            } else if (textResult.contains(("올림")) == true) {
                                int j = textResult.indexOf("올림");
                                writer = textResult.substring(j - 4, j - 1);
                            } else {
                                writer = "발신자 입력";
                            }
                            writer_.setText(writer);

                            Log.d("kakao", "Kata fetch success");
                        }catch (NullPointerException e){
                            textView.setText("인식불가");
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t){
                        Log.d("retrofit",t.getMessage());
                    }
                });
            }
        }


    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI   (@NonNull Context context, @NonNull Uri uri) {//uri 가지고 realpath구하는 함수
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();

        File file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);

            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }

        return file.getAbsolutePath();

    }


    public Bitmap erode_cv(File file){//이미지 전처리

        OpenCVLoader.initDebug();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap,src);
        Mat temp = new Mat();
        Mat conclusion = new Mat();
        org.opencv.core.Size size=new Size(3,3);
        Imgproc.GaussianBlur(src,src,size,1f);
        Imgproc.erode(src,temp, Imgproc.getStructuringElement(Imgproc.MORPH_RECT,size));
        Imgproc.dilate(temp,conclusion,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,size));
        Imgproc.threshold(conclusion,conclusion,127,255,Imgproc.THRESH_BINARY);

        Utils.matToBitmap(conclusion,bitmap);
        return bitmap;

    }

    private File createImageFile() throws IOException {//카메라로 찍고나서 이미지 저장 함수
        // 파일이름을 세팅 및 저장경로 세팅
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File StorageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                StorageDir
        );

        return image;
    }



    private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {//비트맵을 파일로 바꿔주는 함수
        File fileCacheItem = new File(strFilePath);
        OutputStream out = null;
        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); }
        catch (Exception e) { e.printStackTrace(); }
        finally { try { out.close(); }
        catch (IOException e) { e.printStackTrace(); }
        }
    }

}