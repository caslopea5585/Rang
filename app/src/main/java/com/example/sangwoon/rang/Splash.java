package com.example.sangwoon.rang;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       Handler hd = new Handler();
                hd.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();       // 3 초후 이미지를 닫아버림
                    }
                }, 3000);
        }
}
