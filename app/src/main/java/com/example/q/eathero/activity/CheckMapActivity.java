package com.example.q.eathero.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
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
    private BitmapDescriptor myBitmap;
    private BaiduMap baiduMap;
    private TextView shopNameTxt;
    private TextView specialTxt;
    private TextView rankTxt;
    private View dialogView;
    private Dialog dialog;
    private Button enterDetailBtn;

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
                for (final ShopBean shopBean : list) {
                    final String latitude = shopBean.getLatitude();
                    String longitude = shopBean.getLongitude();
                    final int rank = shopBean.getRank();
                    final String name = shopBean.getShopName();
                    final String description = shopBean.getDescription();
                    final String comment=shopBean.getComment();
                    LogUtil.e(TAG, "latitude:" + latitude);
                    final LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    MarkerOptions overlay = new MarkerOptions().icon(bitmap).position(latLng);
                    baiduMap.addOverlay(overlay);

                    baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(final Marker marker) {
                            //这里的marker是所有的marker
                            if (marker.getPosition() == latLng) {
                                dialogView.setVisibility(View.INVISIBLE);
                                LogUtil.e(TAG, marker.getPosition().toString());
                                MapViewLayoutParams layoutParams = new MapViewLayoutParams.Builder()
                                        .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)//按照经纬度设置位置
                                        .position(marker.getPosition())//不能传null
                                        .width(MapViewLayoutParams.WRAP_CONTENT)
                                        .height(MapViewLayoutParams.WRAP_CONTENT)
                                        .yOffset(-5)//距离position的像素 向下是正值，向上是负值
                                        .build();
                                shopNameTxt.setText(name);
                                specialTxt.setText(description);
                                rankTxt.setText(String.valueOf(rank));
                                enterDetailBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(CheckMapActivity.this,ShopDetailActivity.class);
                                        //+添加图片
                                        intent.putExtra("shopName",name);
                                        intent.putExtra("special",description);
                                        intent.putExtra("assess",comment);
                                        intent.putExtra("rank",rank);
                                        startActivity(intent);
                                    }
                                });
                                mapView.updateViewLayout(dialogView, layoutParams);
                                dialogView.setVisibility(View.VISIBLE);
                            }

                            return false;
                        }
                    });
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
        myBitmap=BitmapDescriptorFactory.fromResource(R.drawable.my_location_ico);
        initDialog();
    }

    private void initDialog() {
        dialogView = View.inflate(this, R.layout.dialog_base_shop_info, null);
        shopNameTxt = (TextView) dialogView.findViewById(R.id.shop_name_txt);
        specialTxt = (TextView) dialogView.findViewById(R.id.special_txt);
        rankTxt = (TextView) dialogView.findViewById(R.id.rank_txt);
        enterDetailBtn = (Button) dialogView.findViewById(R.id.enter_detail_btn);
        MapViewLayoutParams layoutParams = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)//按照经纬度设置位置
                .position(new LatLng(0, 0))//不能传null，设置为mapMode时，必须设置position
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .build();

        mapView.addView(dialogView, layoutParams);
        dialogView.setVisibility(View.INVISIBLE);
    }

    private void initDate() {
        //设置定图层
        sp = getSharedPreferences("config", MODE_PRIVATE);
        MyLocationData locData = new MyLocationData.Builder()
                .latitude(Double.parseDouble(sp.getString("nowLatitude", "60")))
                .longitude(Double.parseDouble(sp.getString("nowLongitude", "60"))).build();
        baiduMap.setMyLocationData(locData);
        MyLocationConfiguration config = new MyLocationConfiguration(null, true, myBitmap);
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
