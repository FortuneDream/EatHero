package com.example.q.eathero.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.q.eathero.R;

public class SplashActivity extends AppCompatActivity {
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
