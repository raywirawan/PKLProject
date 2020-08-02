package com.myproject.pkl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.pkl.MyUtils;
import com.myproject.pkl.R;
import com.myproject.pkl.model.Database;
import com.myproject.pkl.model.IdentifiedObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult, tvStatus, tvJenis;
    private ImageView ivSuccess, ivFail, ivResultImage;
    private ImageButton btnBack;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        database = Database.getInstance(this);

        btnBack = findViewById(R.id.btn_result_back);
        tvResult = findViewById(R.id.tv_result_message);
        tvStatus = findViewById(R.id.tv_status_message);
        tvJenis = findViewById(R.id.tv_jenis_message);
        ivSuccess = findViewById(R.id.icon_success);
        ivFail = findViewById(R.id.icon_failed);
        ivResultImage = findViewById(R.id.iv_result_image);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onBackPressed(); }
        });

        String result = this.getIntent().getStringExtra("result");
        byte[] photoByte = this.getIntent().getByteArrayExtra("photo");
        Bitmap bmp = null;
        if(photoByte != null)
        bmp = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

        if (result.contains("Success")){
            ivResultImage.setVisibility(View.VISIBLE);
            if(bmp != null)
            ivResultImage.setImageBitmap(bmp);
            JSONObject jsonResult = new JSONObject();
            try { jsonResult = new JSONObject(result); }
            catch (JSONException e) { e.printStackTrace(); }
            ivFail.setVisibility(View.INVISIBLE);
            ivSuccess.setVisibility(View.VISIBLE);
            tvJenis.setVisibility(View.VISIBLE);
            tvStatus.setText("Classification Success");
            String jenis = "";
            try { jenis = jsonResult.getString("payload"); }
            catch (JSONException e) { e.printStackTrace(); }
            tvJenis.setText("Nilam ini termasuk varietas "+jenis);

            IdentifiedObject io = new IdentifiedObject(jenis, MyUtils.getCurrentDate(), MyUtils.getCurrentClock(), photoByte);
            new ResultActivity.InsertDB().execute(io);

        } else  {
            tvStatus.setText("Classification Failed");
            ivSuccess.setVisibility(View.INVISIBLE);
            ivFail.setVisibility(View.VISIBLE);
        }
        tvResult.setText(result);
    }
    private class InsertDB extends AsyncTask<IdentifiedObject, Void, String> {
        private Exception exception;
        protected String doInBackground(IdentifiedObject... io) {
            try {
                database.identifiedObjectDao().insertIdentifiedObject(io[0]);
                return "db insert success";
            } catch (Exception e) {
                this.exception = e;
                return exception.getMessage();
            }
        }
        protected void onPostExecute(String res) {
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
        }
    }
}