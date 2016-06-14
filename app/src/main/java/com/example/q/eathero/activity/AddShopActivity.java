package com.example.q.eathero.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.q.eathero.R;
import com.example.q.eathero.model.ShopBean;

//添加店铺
public class AddShopActivity extends AppCompatActivity {
    private static final String TAG = "AddShopActivity";
    private EditText longitudeEdt;
    private EditText latitudeEdt;
    private Button addShopBtn;
    private Button openMapBtn;
    private EditText shopNameEdt;
    private EditText specialEdt;
    private ImageView shopPhotoImg;
    private ImageView rankImg;
    private EditText assessEdt;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        initData();
        findView();
        setListener();
    }

    private void initData() {
        sp=getSharedPreferences("config",MODE_PRIVATE);
    }

    private void setListener() {
        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = latitudeEdt.getText().toString().trim();
                String longitude = longitudeEdt.getText().toString().trim();
                String shopName = shopNameEdt.getText().toString().trim();
                String description = specialEdt.getText().toString().trim();
                String commit = assessEdt.getText().toString().trim();
                //还需要得到星级和图片
                if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(shopName) || TextUtils.isEmpty(description) || TextUtils.isEmpty(commit)) {
                    Toast.makeText(getApplicationContext(), "请填入完整的信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                ShopBean shopBean=new ShopBean();
                shopBean.setLatitude(latitude);
                shopBean.setLongitude(longitude);
                shopBean.setShopName(shopName);
                shopBean.setDescription(description);
                shopBean.setComment(commit);
                shopBean.setRank(2);
                Toast.makeText(getApplicationContext(),"已上传数据",Toast.LENGTH_SHORT).show();
                //数据上传至服务器,包括图片等
            }
        });
        //打开百度地图
        openMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShopActivity.this, AddMapActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void findView() {
        longitudeEdt = (EditText) findViewById(R.id.longitude_edt);
        latitudeEdt = (EditText) findViewById(R.id.latitude_edt);
        addShopBtn = (Button) findViewById(R.id.add_shop_btn);
        openMapBtn = (Button) findViewById(R.id.open_map_btn);
        shopNameEdt = (EditText) findViewById(R.id.shop_name_edt);
        specialEdt = (EditText) findViewById(R.id.special_edt);
        shopPhotoImg = (ImageView) findViewById(R.id.shop_photo_img);
        rankImg = (ImageView) findViewById(R.id.rank_img);
        assessEdt = (EditText) findViewById(R.id.assess_edt);
    }

    //取出从map里得到的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //取出数据,显示在edit中
        if (resultCode == 1) {
            String longitude = String.valueOf(data.getDoubleExtra("longitude",60));
            String latitude = String.valueOf(data.getDoubleExtra("latitude",60));
            Log.e(TAG,"已经取出数据:"+"latitude:"+ latitude+" longitude:"+longitude);
            longitudeEdt.setText(longitude);
            latitudeEdt.setText(latitude);
        }else {
            //直接返回没有选择地点，此处应该设置为当前的位置
            longitudeEdt.setText(sp.getString("longitude","60"));
            latitudeEdt.setText(sp.getString("latitude","60"));
        }
    }
}
