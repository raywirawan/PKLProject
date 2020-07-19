package com.myproject.pkl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.common.util.IOUtils;
import com.myproject.pkl.R;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CameraView";
    public static final int PICK_IMAGE = 1;
    private ImageButton btn_capture, btn_pickImage, btn_switchCam;
    private ImageView btn_closeCam;
    private CameraView camera;
    private ProgressDialog dialog;
    private String sharedPrefFile = "com.myproject.pkl.preferences";
    private SharedPreferences sharedPref;
    private String savedUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        sharedPref = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        savedUrl = sharedPref.getString("url", "https://proyekmagangpkl2020.herokuapp.com/predict");

        dialog = new ProgressDialog(PhotoActivity.this);
        dialog.setMessage("Uploading Image...");

        btn_capture = findViewById(R.id.btn_camera_capture);
        btn_pickImage = findViewById(R.id.btn_pick_image);
        btn_switchCam = findViewById(R.id.btn_reverse_camera);
        btn_closeCam = findViewById(R.id.btn_close_camera);

        btn_closeCam.setOnClickListener(this);
        btn_capture.setOnClickListener(this);
        btn_pickImage.setOnClickListener(this);
        btn_switchCam.setOnClickListener(this);

        // Create an instance of Camera View
        camera = findViewById(R.id.camera_preview);
        camera.setLifecycleOwner(this);

        // Create our Preview view and set it as the content of our activity.
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(PictureResult result) {

                // Access the raw data and process
                byte[] data = result.getData();
                new PhotoActivity.UploadTask().execute(data);
                dialog.show();
                camera.close();
            }
        });
    }
    //Implicit intent listener
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "RESULT CODE: $resultCode");
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                byte[] bytes = IOUtils.toByteArray(is);
                dialog.show();
                new PhotoActivity.UploadTask().execute(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Background thread for networking task
    private class UploadTask extends AsyncTask<byte[], Void, String> {
        private Exception exception;
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        protected String doInBackground(byte[]... image) {
            try {
                //compress image
                Bitmap img = BitmapFactory.decodeByteArray(image[0], 0, image[0].length);
                Bitmap scaled_img = Bitmap.createScaledBitmap(img, 400, 500, true);
                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                scaled_img.compress(Bitmap.CompressFormat.JPEG, 100, blob);
                byte[] upload = blob.toByteArray();

                //upload image using http post
                System.out.println("UPLOADING IMAGE");
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image","upload.jpg",
                                RequestBody.create(MediaType.parse("image/*jpg"), upload))
                        .build();
                Request request = new Request.Builder()
                        .url(savedUrl)
                        .method("POST", body)
                        .build();
                Response response = client.newCall(request).execute();
                intent.putExtra("photo", upload);
                return response.body().string();

            } catch (Exception e) {
                this.exception = e;
                return exception.getMessage();
            }
        }
        protected void onPostExecute(String res) {
            dialog.dismiss();
            intent.putExtra("result", res);
            startActivity(intent);
        }
    }

    //UI event Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_camera_capture:
                camera.takePicture();
                break;
            case R.id.btn_pick_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
            case R.id.btn_reverse_camera:
                if (camera.getFacing()== Facing.BACK){
                    camera.setFacing(Facing.FRONT);
                } else {
                    camera.setFacing(Facing.BACK);
                }
                break;
            case R.id.btn_close_camera:
                onBackPressed();
                break;
        }
    }
}