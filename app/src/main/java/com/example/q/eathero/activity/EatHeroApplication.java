package com.example.q.eathero.activity;

import android.app.Application;
import com.baidu.mapapi.SDKInitializer;
import com.example.q.eathero.imp.UILImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;


/**
 * Created by Q on 2016/6/14.
 */
public class EatHeroApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        Bmob.initialize(this, "ce3d7dc8b1f20796e7f3e4d2170131b5");
        Logger.init().setLogLevel(LogLevel.FULL);
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
        ThemeConfig theme=new ThemeConfig.Builder().build();
        FunctionConfig function=new FunctionConfig.Builder()
                .setEnableCamera(true).setEnableEdit(true)
                .setEnableCrop(true).setEnableRotate(true)
                .setCropSquare(true).setEnablePreview(true).build();
        cn.finalteam.galleryfinal.ImageLoader loader=new UILImageLoader();//图片加载器
        CoreConfig coreConfig=new CoreConfig.Builder(this,loader,theme).setFunctionConfig(function).build();
        GalleryFinal.init(coreConfig);

    }

}
