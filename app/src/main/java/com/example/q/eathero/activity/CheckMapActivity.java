package com.example.q.eathero.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.q.eathero.R;
import com.example.q.eathero.model.ShopBean;
import com.example.q.eathero.util.LogUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CheckMapActivity extends AppCompatActivity {
    private static final String TAG = "CheckMapActivity";
    private SharedPreferences sp;
    private TextureMapView mapView;
    private BitmapDescriptor bitmap;
    private BaiduMap baiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_map);
        initView();
        initDate();
        setView();
    }

    private void setView() {
        BmobQuery<ShopBean> shopBeanBmobQuery = new BmobQuery<>();
        shopBeanBmobQuery.findObjects(this, new FindListener<ShopBean>() {
            @Override
            public void onSuccess(List<ShopBean> list) {
                LogUtil.e(TAG, "当前线程：" + Thread.currentThread());
                for (ShopBean shopBean : list) {
                    String latitude = shopBean.getLatitude();
                    String longitude = shopBean.getLongitude();
                    LogUtil.e(TAG,"latitude:"+latitude);
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    MarkerOptions overlay = new MarkerOptions().icon(bitmap).position(latLng);
                    baiduMap.addOverlay(overlay);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        //连接服务器，得到数据，显示当前坐标和周边的小吃店
    }

    private void initView() {
        mapView = (TextureMapView) findViewById(R.id.check_map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);//开启定位图层
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);//图标
    }

    private void initDate() {
        //设置定图层
        sp = getSharedPreferences("config", MODE_PRIVATE);
        MyLocationData locData = new MyLocationData.Builder()
                .latitude(Double.parseDouble(sp.getString("nowLatitude", "60")))
                .longitude(Double.parseDouble(sp.getString("nowLongitude", "60"))).build();
        baiduMap.setMyLocationData(locData);
        MyLocationConfiguration config = new MyLocationConfiguration(null, true, bitmap);
        baiduMap.setMyLocationConfigeration(config);

        //设定中心点坐标
        LatLng center = new LatLng(Double.parseDouble(sp.getString("nowLatitude", "60")), Double.parseDouble(sp.getString("nowLongitude", "60")));
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(center).zoom(200)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
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
