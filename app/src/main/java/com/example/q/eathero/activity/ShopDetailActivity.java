package com.example.q.eathero.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.eathero.R;

public class ShopDetailActivity extends AppCompatActivity {
    private TextView shopNameTxt;
    private TextView specialTxt;
    private ImageView shopPhotoImg;
    private TextView assessTxt;
    private TextView rankTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        findView();
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        String shopName=intent.getStringExtra("shopName");
        String special=intent.getStringExtra("special");
        String assess=intent.getStringExtra("assess");
        int rank=intent.getIntExtra("rank",0);
        shopNameTxt.setText(shopName);
        specialTxt.setText(special);
        assessTxt.setText(assess);
        rankTxt.setText(String.valueOf(rank));
    }

    private void findView() {
        shopNameTxt = (TextView) findViewById(R.id.shop_name_txt);
        specialTxt = (TextView) findViewById(R.id.special_txt);
        shopPhotoImg = (ImageView) findViewById(R.id.shop_photo_img);
        assessTxt = (TextView) findViewById(R.id.assess_txt);
        rankTxt = (TextView) findViewById(R.id.rank_txt);
    }
}
