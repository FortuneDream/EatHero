package com.example.q.eathero.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.q.eathero.R;

public class SplashActivity extends AppCompatActivity {

    private Button mapBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mapBtn = (Button) findViewById(R.id.map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SplashActivity.this,AddMapActivity.class);
                startActivity(intent);
            }
        });
    }
}
