package com.example.q.eathero.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;

import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.q.eathero.R;


//添加标记
public class AddMapActivity extends AppCompatActivity {
    private static final String TAG = "BaiduMap";
    private BitmapDescriptor bitmap;
    private MapView mapView;
    private Overlay icoMarker;
    private Overlay textMarker;
    OverlayOptions icoOptions;
    OverlayOptions textOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map_view);
        final BaiduMap baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //baiduMap.setTrafficEnabled(true);//实时交通
        //baiduMap.setBaiduHeatMapEnabled(true);热力图
        //final LatLng point = new LatLng(39.963175, 116.400244);//定义Marker坐标点
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.default_ico);//图标

        //点击设置图标+文字标记（--->添加确定按钮+上传位置信息+每次进入界面根据已标记画图）
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (icoMarker != null && textMarker != null) {
                    icoMarker.remove();
                    textMarker.remove();
                }
                icoOptions = new MarkerOptions().icon(bitmap).position(latLng);
                textOptions = new TextOptions().bgColor(0xAAFFFF00).fontSize(24).fontColor(0xFFFF00FF).text("这个点").position(latLng);
                icoMarker = baiduMap.addOverlay(icoOptions);
                textMarker = baiduMap.addOverlay(textOptions);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });


//        OverlayOptions options = new MarkerOptions().position(point).icon(bitmap).zIndex(9).draggable(true);//设置拖动
//        Marker marker = (Marker) (baiduMap.addOverlay(options));
//        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDrag(Marker marker) {
//                Log.e(TAG, "正在拖拽");
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                Log.e(TAG, "拖拽结束");
//                LatLng point2 = marker.getPosition();
//                marker.setPosition(point2);
//
//            }
//
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//                Log.e(TAG, "开始拖拽");
//            }
//        });
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
