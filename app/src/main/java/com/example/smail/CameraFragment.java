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
import android.net.http.SslCertificate;
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
import androidx.loader.content.CursorLoader;
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
import java.sql.SQLSyntaxErrorException;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStorageDirectory;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
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

    ImageView imageView;//????????? ????????? ????????? ???
    ImageView opencv_;//opencv test
    EditText textView;// ????????? ??????
    EditText today_date;//?????? ??????
    EditText writer_;//writer ???????????? default ?????????
    TextView subtextView;
    Uri uri;
    private String imageUrl="";
    double rotate_=0f;

    private FirebaseStorage storage;
    private FirebaseDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carmeratab,container,false);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        OpenCVLoader openCVLoader;
        Button bt_photo=view.findViewById(R.id.btn_photo);
        Button cameraButton=view.findViewById(R.id.cameraButton);
        Button bt_save =view.findViewById(R.id.save_Btn);
        bt_photo.setOnClickListener(photoClick);
        cameraButton.setOnClickListener(cameraClick);
        bt_save.setOnClickListener(saveClick);

        imageView = (ImageView) view.findViewById(R.id.iv_photo);
        subtextView = (TextView) view.findViewById(R.id.text_sub);

        textView = (EditText) view.findViewById(R.id.ocr_text);
        today_date = (EditText) view.findViewById(R.id.today_date);
        writer_ = (EditText) view.findViewById(R.id.writer);
        opencv_=(ImageView) view.findViewById(R.id.opencv_);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "?????? ?????? ??????"); } else { Log.d(TAG, "?????? ?????? ??????");
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        return view;
    }
    View.OnClickListener photoClick = new View.OnClickListener(){//????????? ?????? ???????????? ??????
        @Override
        public void onClick(View view) {
            selectImage();

        }
    };

    View.OnClickListener saveClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
                uploadImg(imageUrl);

        }
    };

    View.OnClickListener cameraClick = new View.OnClickListener(){//????????? ?????? ??????
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cameraButton:
                    dispatchTakePictureIntent();//?????? ????????? ?????? ????????? ????????? ?????? ?????? ??? ??????
                    break;
            }

        }
    };

    private void uploadImg(String uri)
    {
        try {
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            Uri file = Uri.fromFile(new File(uri));
            System.out.println(file + "????????????");

            final StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getActivity(), "????????? ??????", Toast.LENGTH_SHORT).show();

                        //????????????????????? ?????????????????? ?????????
                        @SuppressWarnings("VisibleForTests")
                        Uri downloadUrl = task.getResult();

                        Model model = new Model();
                        model.imageUrl = downloadUrl.toString();
                       // model.setImageUrl(downloadUrl.toString());
                        model.description = textView.getText().toString();
                        model.date = today_date.getText().toString();
                        model.sender = writer_.getText().toString();
                       // model.setTitle(etTitle.getText().toString());
                       // model.setDescription(etDesc.getText().toString());
                        //model.setUid(mAuth.getCurrentUser().getUid());
                        //model.setUserId(mAuth.getCurrentUser().getEmail());

                        //image ?????? ???????????? json ????????? ?????????.
                        //database.getReference().child("Profile").setValue(imageDTO);
                        //  .push()  :  ???????????? ?????????.
                        database.getReference().child("Profile").push().setValue(model);

                        //Intent intent = new Intent(getActivity().getApplicationContext(), CameraFragment.class);
                        //startActivity(intent);

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        } catch (NullPointerException e)
        {
            Toast.makeText(getActivity(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri)
    {
        String[] proj=  {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getContext(),uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return  url;
    }



    private void dispatchTakePictureIntent() {//????????? ?????? ?????? ??? ????????? ???????????? intent ?????? ????????? ???????????? uri ???????????? ??????
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                uri = FileProvider.getUriForFile(getActivity(),
                        "com.example.smail",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takePictureIntent,TAKE_PICTURE);
            }
        }
    }

    private void selectImage(){//????????? ????????????
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);

    }

    public void onActivityResult(int requestCode, int resultCode,Intent intent){

        switch(requestCode){
            case 100://?????????
                if(resultCode == RESULT_OK){
                    Uri fileUrl = intent.getData();
                    imageUrl = getRealPathFromURI(getActivity(),intent.getData());
                    if(fileUrl != null){
                        uploadFile(fileUrl);

                        imageView.setImageURI(fileUrl);
                        subtextView.setVisibility(View.GONE);
                    }
                }
                break;
            case TAKE_PICTURE://?????????
                if (resultCode == RESULT_OK ) {
                    File file = new File(mCurrentPhotoPath);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media
                                .getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {//???????????? ???????????? ????????? ?????? ?????? ?????? ?????? if???
                        ExifInterface ei = null;
                        try {
                            ei = new ExifInterface(mCurrentPhotoPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        System.out.println(orientation);
                        switch(orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90://90??? ??????
                                rotatedBitmap = rotateImage(bitmap, 90);

                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180://180??? ??????
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270://270??? ??????
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }
                        imageView.setImageBitmap(rotatedBitmap);
                        subtextView.setVisibility(View.GONE);


                    }

                   // Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                   // Uri fileUrl = getImageUri(getActivity(),bitmap);
                    if (bitmap != null) {
                        uploadFile(uri);
                        imageUrl = getRealPathFromURI(getActivity(),uri);
                    }
                } break;
        }
        super.onActivityResult(requestCode,resultCode,intent);
    }

    private void uploadFile(Uri fileUri){//kakao api ???????????? ??????
        KakaoClient kakaoClient = KakaoClient.getInstance();
        KakaoPhotoInterface kakaoPhotoInterface = KakaoClient.getRetrofitInterface();


        System.out.println(fileUri);
        System.out.println(fileUri.getPath());
        String filePath = getRealPathFromURI(getActivity(),fileUri);//uri?????? realfilepath ????????????

        System.out.println(filePath);
        if(filePath !=null && !filePath.isEmpty()){
            File file = new File(filePath);

            //????????? ??????

            System.out.println(file.exists()+"file.exists");

            if(file.exists()){
                RequestBody requestFile = RequestBody.create(MediaType.parse("form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);
                kakaoPhotoInterface.getPhotoFileResult(body).enqueue(new Callback<JsonObject>(){
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response){//kakao api ?????? ?????? ??? ??????

                        System.out.println(response.body().toString());

                        try {
                            Gson gson = new Gson();
                            recognizedWord RecognizeWord = gson.fromJson(response.body(), recognizedWord.class);
                            List<Result> word = RecognizeWord.getResult();
                            float rot = (word.get(0).getBoxes().get(0).get(1)-word.get(0).getBoxes().get(1).get(1));
                            float ttt = (word.get(0).getBoxes().get(0).get(0)-word.get(0).getBoxes().get(1).get(0));
                            System.out.println(rot/ttt);
                            rotate_=(Math.atan2(rot,ttt))*180/Math.PI-180;
                            System.out.println(rotate_+"djWWWJfkkrh");
                            SaveBitmapToFileCache(erode_cv(file),filePath);
                            uploadFile_final(file);

                        }catch (NullPointerException e){
                            textView.setText("????????????");
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



    private void uploadFile_final(File file){//kakao api ???????????? ??????
        KakaoClient kakaoClient = KakaoClient.getInstance();
        KakaoPhotoInterface kakaoPhotoInterface = KakaoClient.getRetrofitInterface();

            if(file.exists()){
                RequestBody requestFile = RequestBody.create(MediaType.parse("form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);
                kakaoPhotoInterface.getPhotoFileResult(body).enqueue(new Callback<JsonObject>(){
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response){//kakao api ?????? ?????? ??? ??????

                        System.out.println(response.body().toString());

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
                                    //??????
                                    textResult += eu.get(j);
                                }

                            }
                            if(textResult==""){
                                textView.setText("????????????");
                            }else{
                                textView.setText(textResult);
                                System.out.print(textResult);
                            }

                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy??? MM??? dd???");
                            Date date = new Date();
                            String date_ = format1.format(date);
                            today_date.setText(date_);
                            if (textResult.contains(("???")) == true) {//????????? ????????????
                                int j = textResult.indexOf("???");
                                writer = textResult.substring(j - 4, j - 1);
                            } else if (textResult.contains(("??????")) == true) {
                                int j = textResult.indexOf("??????");
                                writer = textResult.substring(j - 4, j - 1);
                            } else {
                                writer = "????????? ??????";
                            }
                            writer_.setText(writer);

                            Log.d("kakao", "Kata fetch success");
                        }catch (NullPointerException e){
                            textView.setText("????????????");
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t){
                        Log.d("retrofit",t.getMessage());
                    }
                });
            }



    }
    public static Bitmap rotateImage(Bitmap source, float angle) {//???????????? ????????? 90??? ???????????? ????????? ?????? ????????? ??????
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    private Uri getImageUri(Context context, Bitmap inImage) {//????????? ??????????????? uri???????????? ??????, ?????? ?????? ??? ??????
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI   (@NonNull Context context, @NonNull Uri uri) {//uri ????????? realpath????????? ??????
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


    public Bitmap erode_cv(File file){//????????? ?????????

        int Limit=1024;
        OpenCVLoader.initDebug();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        System.out.println(orientation);
        switch(orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90://90??? ??????
                rotatedBitmap = rotateImage(bitmap, 90);

                break;

            case ExifInterface.ORIENTATION_ROTATE_180://180??? ??????
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270://270??? ??????
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        Mat src = new Mat();
        Utils.bitmapToMat(rotatedBitmap,src);
        Mat temp = new Mat();
        Mat conclusion = new Mat();
        org.opencv.core.Size size=new Size(src.width(),src.height());
        float ratio= (float) Limit/Math.max(src.height(),src.width());
        System.out.println(src.height());
        System.out.println(src.width());
        Point point = new Point(src.size().width/2.0,src.size().height/2.0);
        Imgproc.resize(src,temp,size,ratio,ratio);
        Mat rotationMat = Imgproc.getRotationMatrix2D(point,rotate_,1.0);
        org.opencv.core.Size size2=new Size(3,3);
        Imgproc.warpAffine(temp, temp, rotationMat, new Size(0.0,0.0));


        /*Imgproc.GaussianBlur(src,src,size,1f);
        Imgproc.erode(src,temp, Imgproc.getStructuringElement(Imgproc.MORPH_RECT,size));
        Imgproc.dilate(temp,conclusion,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,size));
        Imgproc.threshold(conclusion,conclusion,127,255,Imgproc.THRESH_BINARY);

        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.adaptiveThreshold(src, src, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 2);
        Imgproc.medianBlur(src, conclusion, 7);*/


        Imgproc.cvtColor(temp, conclusion, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.threshold(conclusion,conclusion,160,255,Imgproc.THRESH_BINARY_INV);
        Imgproc.GaussianBlur(conclusion,conclusion,size2,0f);




        //Imgproc.erode(src,temp, Imgproc.getStructuringElement(Imgproc.MORPH_RECT,size));
        //Imgproc.bilateralFilter(temp,temp,);
        //  Imgproc.dilate(temp,conclusion,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,size));

        Utils.matToBitmap(conclusion,rotatedBitmap);

        opencv_.setImageBitmap(rotatedBitmap);
        return rotatedBitmap;

    }

    private File createImageFile() throws IOException {//???????????? ???????????? ????????? ?????? ??????
        // ??????????????? ?????? ??? ???????????? ??????
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File StorageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                StorageDir
        );

        mCurrentPhotoPath=image.getAbsolutePath();
        return image;
    }



    private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {//???????????? ????????? ???????????? ??????
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