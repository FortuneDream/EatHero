package com.example.q.eathero.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.q.eathero.R;
import com.example.q.eathero.model.ShopBean;
import com.example.q.eathero.util.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

//添加店铺
public class AddShopActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AddShopActivity";
    private EditText longitudeEdt;
    private EditText latitudeEdt;
    private Button addShopBtn;
    private Button openMapBtn;
    private EditText shopNameEdt;
    private EditText specialEdt;
    private ImageView shopPhotoImg;
    private ImageView rank1Img;
    private ImageView rank2Img;
    private ImageView rank3Img;
    private ImageView rank4Img;
    private ImageView rank5Img;
    private int rank;
    private EditText assessEdt;
    private SharedPreferences sp;
    private ArrayList<ImageView> ranks;
    private ImageLoader loader;
    private String phtopPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        loader=ImageLoader.getInstance();
        findView();
        initData();
        setListener();
    }

    private void initData() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        ranks = new ArrayList<>();
    }

    private void setListener() {
        rank1Img.setOnClickListener(this);
        rank2Img.setOnClickListener(this);
        rank3Img.setOnClickListener(this);
        rank4Img.setOnClickListener(this);
        rank5Img.setOnClickListener(this);
        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = latitudeEdt.getText().toString().trim();
                String longitude = longitudeEdt.getText().toString().trim();
                String shopName = shopNameEdt.getText().toString().trim();
                String description = specialEdt.getText().toString().trim();
                String commit = assessEdt.getText().toString().trim();
                //还需要得到星级和图片
                if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(shopName) || TextUtils.isEmpty(description) || TextUtils.isEmpty(commit) || ranks.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "请填入完整的信息", Toast.LENGTH_SHORT).show();
                    LogUtil.e(TAG, "latitude:" + latitude + " longitude:" + longitude + " shopName:" + shopName + " description:" + description + " size:" + ranks.size() + " commit:" + commit);
                    return;
                }
                ShopBean shopBean = new ShopBean();
                LogUtil.e(TAG,"photoPath:"+phtopPath);
                BmobFile bmobFile=new BmobFile(new File(phtopPath));//这里photopath=位置+名字，但是在服务器上只能显示名字，而要下载需要网址+名字，这里无解啊。
                bmobFile.uploadblock(AddShopActivity.this, new UploadFileListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

                shopBean.setLatitude(latitude);
                shopBean.setLongitude(longitude);
                shopBean.setShopName(shopName);
                shopBean.setDescription(description);
                shopBean.setComment(commit);
                shopBean.setRank(ranks.size());
                shopBean.save(AddShopActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "已上传数据", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "ranks.size=" + ranks.size());
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplicationContext(), "上传失败:"+s, Toast.LENGTH_SHORT).show();
                    }
                });
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
        shopPhotoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGallerySingle(1, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        for (PhotoInfo photoInfo:resultList){
                            phtopPath=photoInfo.getPhotoPath();
                            Uri uri=Uri.parse(phtopPath);
                            shopPhotoImg.setImageURI(uri);
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
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
        assessEdt = (EditText) findViewById(R.id.assess_edt);
        rank1Img = (ImageView) findViewById(R.id.rank1_img);
        rank2Img = (ImageView) findViewById(R.id.rank2_img);
        rank3Img = (ImageView) findViewById(R.id.rank3_img);
        rank4Img = (ImageView) findViewById(R.id.rank4_img);
        rank5Img = (ImageView) findViewById(R.id.rank5_img);

    }

    //取出从map里得到的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //取出数据,显示在edit中
        if (resultCode == 1) {
            String longitude = data.getStringExtra("longitude");
            String latitude = data.getStringExtra("latitude");
            LogUtil.e(TAG, "已经取出数据:" + "latitude:" + latitude + " longitude:" + longitude);
            longitudeEdt.setText(longitude);
            latitudeEdt.setText(latitude);
        } else {
            //直接返回没有选择地点，此处应该设置为当前的位置
            longitudeEdt.setText(sp.getString("nowLongitude","20" ));
            latitudeEdt.setText(sp.getString("nowLatitude", "20"));
        }
    }

    @Override
    public void onClick(View v) {
        for (ImageView ico : ranks) {
            ico.setImageResource(R.drawable.unlike_ico);
        }
        ranks.clear();
        switch (v.getId()) {
            case R.id.rank5_img:
                ranks.add(rank5Img);
            case R.id.rank4_img:
                ranks.add(rank4Img);
            case R.id.rank3_img:
                ranks.add(rank3Img);
            case R.id.rank2_img:
                ranks.add(rank2Img);
            case R.id.rank1_img:
                ranks.add(rank1Img);
        }
        for (ImageView ico : ranks) {
            ico.setImageResource(R.drawable.like_ico);
        }
    }
}
