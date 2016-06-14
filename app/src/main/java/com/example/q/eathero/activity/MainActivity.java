package com.example.q.eathero.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.example.q.eathero.R;
import com.example.q.eathero.service.GPSService;

//传送到添加和查看店面界面
public class MainActivity extends AppCompatActivity {
    private Button addShopBtn;
    private Button checkShopBtn;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启定位服务
        Intent intent=new Intent(this,GPSService.class);
        startService(intent);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    private void setListener() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addShopBtn = (Button) findViewById(R.id.add_shop_btn);
        checkShopBtn = (Button) findViewById(R.id.check_shop_btn);
    }
}
