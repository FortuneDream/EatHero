package com.example.q.eathero.activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.q.eathero.util.LogUtil;
/*
 * Created by Q on 2016/6/16.
 */
public class BaseActivity extends AppCompatActivity {
    public  String TAG="Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG,"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG,"onDestroy");
    }
}
