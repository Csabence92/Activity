package com.bonc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.bonc.activity.operators.ClickListener;

public class MainActivity extends AppCompatActivity {
    private static final int WIFI_REQUESTCODE = 1001 ;
    private Button playButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();

    }

    private void Init(){
        mContext = MainActivity.this;
        RequestPermissions();
        playButton = findViewById(R.id.main_play);
        playButton.setOnClickListener(new ClickListener(MainActivity.this));
    }
    private void RequestPermissions(){
        if (!CheckPermissions(WIFI_REQUESTCODE)){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},WIFI_REQUESTCODE);
        }
    }
    private boolean CheckPermissions(int permission){
        boolean isGranted = false;
        switch (permission){
            case WIFI_REQUESTCODE :
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
                    isGranted = true;
                }
                break;
        }
        return isGranted;
    }
}