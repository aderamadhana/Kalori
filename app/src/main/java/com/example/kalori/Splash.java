package com.example.kalori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {
    private static int loading = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        final String usia = AppPreference.getUSIA(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(usia!=null){
                    Intent apasih = new Intent(Splash.this,MainActivity.class);
                    startActivity(apasih);
                    this.selesai();
                }else{
                    Intent apasih = new Intent(Splash.this,SettingActivity.class);
                    startActivity(apasih);
                    this.selesai();
                }

            }
            private void selesai(){
                finish();
            }
        },loading);
    };
}
