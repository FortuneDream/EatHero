package com.example.q.eathero.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.eathero.R;
import com.example.q.eathero.model.MyUser;
import com.example.q.eathero.util.LogUtil;

import cn.bmob.v3.listener.SaveListener;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private SharedPreferences sp;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isLogin = sp.getBoolean("login", false);
            if (!isLogin) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setCancelable(false);
                builder.setTitle("请选择~");
                builder.setNegativeButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        initRegisterDialog();
                    }
                });
                builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        initLoginDialog();
                    }
                });
                builder.show();
            } else {
                enterHome();
            }


        }
    };

    private void initLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        View view = View.inflate(this, R.layout.dialog_login, null);
        builder.setView(view);
        final Dialog dialog = builder.show();//弹出dialog
        final EditText userIdEdt = (EditText) view.findViewById(R.id.login_user_id_edt);
        final EditText userPasswordEdt = (EditText) view.findViewById(R.id.login_user_password_edt);
        TextView loginTxt = (TextView) view.findViewById(R.id.login_txt);
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdEdt.getText().toString().trim();
                String userPassword = userPasswordEdt.getText().toString().trim();
                if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(SplashActivity.this, "请填入完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyUser user = new MyUser();
                user.setUsername(userId);
                user.setPassword(userPassword);
                user.login(SplashActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        enterHome();
                        Toast.makeText(SplashActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(SplashActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void enterHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setCancelable(false);
        View view = View.inflate(this, R.layout.dialog_register, null);
        builder.setView(view);
        final Dialog dialog = builder.show();//弹出dialog
        final EditText userIdEdt = (EditText) view.findViewById(R.id.register_user_id_edt);
        final EditText userPasswordEdt = (EditText) view.findViewById(R.id.register_user_password_edt);
        final EditText userNameEdt = (EditText) view.findViewById(R.id.register_user_name_edt);
        TextView registerTxt = (TextView) view.findViewById(R.id.register_txt);
        LogUtil.e(TAG, "registerDialog建立");
        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(TAG, "确定点击事件响应");
                String userId = userIdEdt.getText().toString().trim();
                String userPassword = userPasswordEdt.getText().toString().trim();
                String userName = userNameEdt.getText().toString().trim();
                if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userName)) {
                    Toast.makeText(SplashActivity.this, "请填入完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                LogUtil.e(TAG, "得到输入信息" + "userId:" + userId + " userPassword:" + userPassword + "userName:" + userName);
                MyUser user = new MyUser();
                user.setUsername(userId);
                user.setPassword(userPassword);//MD5加密
                user.setName(userName);
                user.signUp(SplashActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        enterHome();
                        Toast.makeText(SplashActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(SplashActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                });
                LogUtil.e(TAG, "已登录");
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        mHandler.sendEmptyMessageDelayed(0, 1500);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
