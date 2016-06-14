package com.example.q.eathero.activity;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;

/**
 * Created by Q on 2016/6/14.
 */
public class EatHeroApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        Bmob.initialize(this,"ce3d7dc8b1f20796e7f3e4d2170131b5");
    }
}
