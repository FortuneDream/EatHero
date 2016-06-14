package com.example.q.eathero.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.example.q.eathero.R;

public class CheckMapActivity extends AppCompatActivity {
    private MapView mapView;
    private BitmapDescriptor bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_check_map);
        mapView= (MapView) findViewById(R.id.map_view);
        BaiduMap baiduMap=mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.default_ico);//图标

        //连接服务器，得到数据，显示当前坐标和周边的小吃店
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

    }
}
