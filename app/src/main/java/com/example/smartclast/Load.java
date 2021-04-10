package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Load extends AppCompatActivity {
    private TextView tv_jiemian,tv_huanying;
    private ProgressBar pb_jindu;
    private int num = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        initActionBar();

        initView();

        initProgressBar();

        initnotable();
    }



    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("智能家居");
        actionBar.setIcon(R.drawable.robot);
    }


    private void initView() {
        tv_jiemian = findViewById(R.id.tv_jiemian);
        tv_huanying = findViewById(R.id.tv_huanying);
        pb_jindu = findViewById(R.id.pb_jindu);
    }

    private void initProgressBar() {
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                pb_jindu.setProgress(++num);

                if (num == 10){
                    tv_jiemian.setText(num + "    正在加载串口配置...........");
                } else if (num == 20){
                    tv_jiemian.setText(num + "    串口配置加载完成...........");
                }else if (num == 30){
                    tv_jiemian.setText(num + "    正在加载界面配置...........");
                } else if (num == 50){
                    tv_jiemian.setText(num + "    界面配置加载完成...........");
                } else if (num == 60){
                    tv_jiemian.setText(num + "    正在初始化界面..........");
                }else if (num == 80){
                    tv_jiemian.setText(num + "    界面初始化完成..........");

                } else if (num == 99){
                    tv_jiemian.setText(num + "进入系统中...........");
                }
                if (num < 100){
                    handler.sendEmptyMessageDelayed(1,50);
                }else {
                    //跳转到login.class
                    Intent intent = new Intent(Load.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        handler.sendEmptyMessage(1);
    }

    private void initnotable() {
        ObjectAnimator animator = new ObjectAnimator().ofFloat(tv_huanying,"TranslationX",0,-1500).setDuration(5000);
        animator.setRepeatCount(-1);
        animator.start();
    }
}