package com.example.smartclast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

public class locked extends AppCompatActivity {

    private ImageView iv_locked,iv_validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked);

        initActionBar();

        initView();

        setView();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("智能家居");
        actionBar.setIcon(R.drawable.robot);
    }

    private void initView() {
        iv_locked = findViewById(R.id.iv_locked);
        iv_validation = findViewById(R.id.iv_validation);
    }

    //效果可能更好，但不是很理解
//    private void setView() {
//        iv_validation.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == motionEvent.ACTION_MOVE){
//                    if (motionEvent.getRawX() >= iv_validation.getX() &&  motionEvent.getRawX() <= iv_validation.getX() + iv_validation.getWidth() - iv_locked.getWidth() ){
//                        iv_locked.setX(motionEvent.getRawX());
//                    }
//                }else if (motionEvent.getAction() == motionEvent.ACTION_UP){
//                    if (iv_locked.getX() >= iv_validation.getX() + iv_validation.getWidth() - iv_locked.getWidth() - 10){
//                        Intent intent = new Intent(locked.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else {
//                        ObjectAnimator animator = new ObjectAnimator().ofFloat(iv_locked, "TranslationX", 0);
//                        animator.setRepeatCount(0);
//                        animator.setDuration(800);
//                        animator.start();
//                    }
//                }
//
//
//                return true;
//            }
//        });
//    }

    private void setView() {
        iv_locked.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_MOVE) {
                    if (motionEvent.getRawX() >= 390 && motionEvent.getRawX() <= 554) {
                        iv_locked.setX(motionEvent.getRawX());
                    }
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= 554) {
                        iv_locked.setX(554);
                        iv_locked.setEnabled(false);
                        Intent intent = new Intent(locked.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        ObjectAnimator animator = new ObjectAnimator().ofFloat(iv_locked,"TranslationX",0).setDuration(800);
                        animator.setRepeatCount(0);
                        animator.start();
                     }
                }



                return true;
            }
        });
    }

}