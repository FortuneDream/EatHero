package com.example.q.eathero.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.q.eathero.R;

//添加店铺
public class AddShopActivity extends AppCompatActivity {

    private TextView locationTxt;
    private EditText latitudeEdt;
    private EditText longitudeEdt;
    private Button addShopBtn;
    private Button openMapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        findView();
        setListener();
    }

    private void setListener() {
        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = latitudeEdt.getText().toString().trim();
                String longitude = longitudeEdt.getText().toString().trim();
                if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
                    Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //上传至服务器
            }
        });
        //打开百度地图
        openMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShopActivity.this, AddMapActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void findView() {
        locationTxt = (TextView) findViewById(R.id.location_txt);
        latitudeEdt = (EditText) findViewById(R.id.latitude_edt);
        longitudeEdt = (EditText) findViewById(R.id.longitude_edt);
        addShopBtn = (Button) findViewById(R.id.add_shop_btn);
        openMapBtn = (Button) findViewById(R.id.open_map_btn);
    }

    //取出从map里得到的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //取出数据,显示在edit中
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            String longitude = bundle.getString("longitude");
            String latitude = bundle.getString("latitude");
            longitudeEdt.setText(longitude);
            latitudeEdt.setText(latitude);
        }
    }
}
