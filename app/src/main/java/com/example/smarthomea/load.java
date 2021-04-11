package com.example.smarthomea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

public class load extends AppCompatActivity {

    private ProgressBar pb_start;
    private TextView tv_load;
    private int pro = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        init_View();

        handler.sendEmptyMessage(1);
    }

    private void init_View() {
        pb_start = findViewById(R.id.pb_start);
        tv_load = findViewById(R.id.tv_load);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                pro++;
                pb_start.setProgress(pro);
                if (pro == 10){
                    tv_load.setText("10   正在加载串口配置...........");
                }else if (pro == 20){
                    tv_load.setText("20   串口配置加载完成...........");
                }else if (pro == 30){
                    tv_load.setText("30   正在加载界面配置...........");
                }else if (pro == 50){
                    tv_load.setText("50   界面配置加载完成........... ");
                }else if (pro == 60){
                    tv_load.setText("60   正在初始化界面..........");
                }else if (pro == 80){
                    tv_load.setText("80   界面初始化完成..........");
                }else if (pro == 100){
                    tv_load.setText("100  进入系统中...........");
                    startActivity(new Intent(load.this,locked.class));
                }
            }
            handler.sendEmptyMessageDelayed(1,50);
        }
    };

}