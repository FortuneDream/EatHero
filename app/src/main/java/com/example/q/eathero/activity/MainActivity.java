package com.example.q.eathero.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.q.eathero.R;
import com.example.q.eathero.engine.LocationService;
import com.example.q.eathero.model.MyUser;
import com.example.q.eathero.model.ShopBean;


import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

//传送到添加和查看店面界面
public class MainActivity extends BaseActivity {
    private SharedPreferences sp;
    private LocationService locationService;
    private TextView userNameTxt;
    private Button addShopBtn;
    private Button checkShopBtn;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private CircleImageView userPhotoCiv;
    private TextView exitTxt;

    //private ListView listView;
    private List<ShopBean> shopBeanList;//在主页得到自己喜欢的店面，用xml存储数组方便全局使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        intDate();
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService.stop(); //停止定位服务
    }

    private void intDate() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("login", true);
        editor.apply();
        locationService = new LocationService(getApplicationContext());
    }


    private void initView() {
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_photo_ico);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbarBackground));
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        userNameTxt.setText(user.getName());
    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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
                Intent intent = new Intent(MainActivity.this, AddShopActivity.class);
                startActivity(intent);
            }
        });
        checkShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckMapActivity.class);
                startActivity(intent);
            }
        });
        exitTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("login", false);
                editor.apply();
                finish();
            }
        });

    }

    private void findView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addShopBtn = (Button) findViewById(R.id.add_shop_btn);
        checkShopBtn = (Button) findViewById(R.id.check_shop_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        userPhotoCiv = (CircleImageView) findViewById(R.id.user_photo_civ);//之后可以自定义头像
        userNameTxt = (TextView) findViewById(R.id.user_name_txt);
        exitTxt = (TextView) findViewById(R.id.exit_txt);
    }

    public void showLocation(View view) {
        locationService.start();
    }

    private class MyColectionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
