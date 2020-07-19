package com.myproject.pkl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myproject.pkl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult, tvStatus;
    private ImageView ivSuccess, ivFail, ivResultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tv_result_message);
        tvStatus = findViewById(R.id.tv_status_message);
        ivSuccess = findViewById(R.id.icon_success);
        ivFail = findViewById(R.id.icon_failed);
        ivResultImage = findViewById(R.id.iv_result_image);

        String result = this.getIntent().getStringExtra("result");
        byte[] photoByte = this.getIntent().getByteArrayExtra("photo");
        Bitmap bmp = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

        if (result.contains("Success")){
            ivResultImage.setVisibility(View.VISIBLE);
            ivResultImage.setImageBitmap(bmp);
            JSONObject jsonResult = new JSONObject();
            try { jsonResult = new JSONObject(result); }
            catch (JSONException e) { e.printStackTrace(); }
            ivFail.setVisibility(View.INVISIBLE);
            ivSuccess.setVisibility(View.VISIBLE);
            try { tvStatus.setText("Classified as "+jsonResult.getString("payload")); }
            catch (JSONException e) { e.printStackTrace(); }
        } else  {
            tvStatus.setText("Classification Failed");
            ivSuccess.setVisibility(View.INVISIBLE);
            ivFail.setVisibility(View.VISIBLE);
        }
        tvResult.setText(result);

    }
}