package com.example.q.eathero.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.q.eathero.R;

public class MainActivity extends AppCompatActivity {
    private Button addShopBtn;
    private Button checkShopBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    private void setListener() {
        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,AddShopActivity.class);
                startActivity(intent);
            }
        });
        checkShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CheckMapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        addShopBtn = (Button) findViewById(R.id.add_shop_btn);
        checkShopBtn = (Button) findViewById(R.id.check_shop_btn);
    }
}
