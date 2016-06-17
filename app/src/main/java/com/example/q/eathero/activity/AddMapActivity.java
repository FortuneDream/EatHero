package com.example.q.eathero.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.example.q.eathero.R;
import com.example.q.eathero.util.LogUtil;


//添加标记
public class AddMapActivity extends BaseActivity {
    private SharedPreferences sp;
    private BitmapDescriptor bitmap;
    private TextureMapView mapView;
    private Overlay icoMarker;
    private Overlay textMarker;
    private OverlayOptions icoOptions;
    private OverlayOptions textOptions;
    private LatLng markerLatLng;
    //    private LatLng nowLatLng;//北纬东经，latitude,longitude
//    private SharedPreferences sp;
    private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initDate();
        setListener();
    }

    private void initDate() {
        //设置定图层
        LogUtil.e(TAG,"initDate Start");
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
        LogUtil.e(TAG,"initDate Over");
    }

    //设置baidumap点击事件
    private void setListener() {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LogUtil.e(TAG,"onMapClick");
                if (icoMarker != null && textMarker != null) {
                    icoMarker.remove();
                    textMarker.remove();
                }
                icoOptions = new MarkerOptions().icon(bitmap).position(latLng);
                textOptions = new TextOptions().bgColor(0xAAFFFF00).fontSize(24).fontColor(0xFFFF00FF).text("这个点").position(latLng);
                icoMarker = baiduMap.addOverlay(icoOptions);
                textMarker = baiduMap.addOverlay(textOptions);
                LogUtil.e(TAG,"已添加点到地图上");
                markerLatLng = latLng;
                Intent intent = new Intent();
                intent.putExtra("longitude", String.valueOf(markerLatLng.longitude));
                intent.putExtra("latitude", String.valueOf(markerLatLng.latitude));
                setResult(1, intent);
                LogUtil.e(TAG,"设置返回信息");
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void initView() {
        LogUtil.e(TAG,"initView Start");
        mapView = (TextureMapView) findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);//开启定位图层
        //final LatLng point = new LatLng(39.963175, 116.400244);//定义Marker坐标点
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);//图标
        LogUtil.e(TAG,"initView Over");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        // 当不需要定位图层时关闭定位图层
        baiduMap.setMyLocationEnabled(false);
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
