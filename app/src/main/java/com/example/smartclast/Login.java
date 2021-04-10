package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity {

    private EditText et_username,et_port,et_ip,et_password;
    private Button btn_login;
    private TextView tv_time1,tv_loading;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActionBar();

        initView();

        setView();

        initAnimator();

        initTime();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("智能家居");
        actionBar.setIcon(R.drawable.robot);
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_port = findViewById(R.id.et_port);
        et_ip = findViewById(R.id.et_ip);
        tv_time1 = findViewById(R.id.tv_time1);
        tv_loading = findViewById(R.id.tv_loading);
        btn_login = findViewById(R.id.btn_login);
    }

    private void setView() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_ip.getText().toString().equals("192.168.10.2")){
                    if (et_port.getText().toString().equals("6006")){
                        if (et_username.getText().toString().equals("bizideal") && et_password.getText().toString().equals("123456")){
                            show(Login.this,"登陆成功");
                            Intent intent = new Intent(Login.this, locked.class);
                            startActivity(intent);
                            finish();
                        }else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
                            alertDialog.setTitle("登陆失败").setMessage("密码或用户名错误").setPositiveButton("Ok",null).show();
                        }
                    }else {
                        show(Login.this,"端口号错误");
                    }
                }else {
                    show(Login.this,"ip非法");
                }
            }
        });

    }

    public static void show(Activity activity, String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAnimator() {
        ObjectAnimator animator=new ObjectAnimator().ofFloat(tv_loading,"Alpha",1,0,1);
        animator.setRepeatCount(-1);
        animator.setDuration(5000);
        animator.start();
    }


    private void initTime() {
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                tv_time1.setText(new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss").format(new Date()));

                handler.sendEmptyMessageDelayed(1,1000);
            }
        };
        handler.sendEmptyMessage(1);
    }

}