package com.example.smarthomea;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class locked extends AppCompatActivity {

    private EditText et_ip;
    private ImageView iv_huadong;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked);

        init_ActionBar();

        init_View();

        set_View();

        set_slide();
    }

    private void init_ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.robot);

    }


    private void init_View() {
        et_ip = findViewById(R.id.et_ip);
        iv_huadong = findViewById(R.id.iv_huadong);
        btn_login = findViewById(R.id.btn_login);
    }


    private void set_View() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_ip.getText().toString().equals("127.0.0.1")){
                    startActivity(new Intent(locked.this,MainActivity.class));
                }else {
                    Toast.makeText(locked.this, "登陆失败，ip错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void set_slide() {
        iv_huadong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==motionEvent.ACTION_MOVE){
                    if (motionEvent.getRawX()>=104 && motionEvent.getRawX() <= 404){
                        iv_huadong.setX(motionEvent.getRawX());
                    }
                }else if (motionEvent.getAction() == motionEvent.ACTION_UP){
                    if (motionEvent.getRawX() >= 404){
                        iv_huadong.setX(404);
                        iv_huadong.setEnabled(false);
                        if (et_ip.getText().toString().equals("127.0.0.1")){
                            startActivity(new Intent(locked.this,MainActivity.class));
                        }else {
                            Toast.makeText(locked.this, "登陆失败，ip错误", Toast.LENGTH_SHORT).show();
                            iv_huadong.setEnabled(true);
                            iv_huadong.setX(104);
                        }
                    }else{
                        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_huadong,"TranslationX",0).setDuration(1000);
                        animator.setRepeatCount(0);
                        animator.start();
                    }
                }

                return true;
            }
        });
    }
}