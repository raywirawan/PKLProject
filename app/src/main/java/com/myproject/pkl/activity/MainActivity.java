package com.myproject.pkl.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myproject.pkl.R;
import com.myproject.pkl.adapter.ListHistoryAdapter;
import com.myproject.pkl.fragment.HomeFragment;
import com.myproject.pkl.fragment.SettingsFragment;
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


public class MainActivity extends AppCompatActivity{

    ListHistoryAdapter listHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.homeFragment, R.id.settingsFragment)
//                .build();

        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.homeFragment :
                        ft.replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                        getSupportActionBar().setTitle("Patchtion");
                        break;
                    case R.id.settingsFragment:
                        ft.replace(R.id.nav_host_fragment,new SettingsFragment()).commit();
                        getSupportActionBar().setTitle("Settings");
                        break;
                }
                return false;
            }
        });
    }
}

