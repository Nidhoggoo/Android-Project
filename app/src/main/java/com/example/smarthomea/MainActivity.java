package com.example.smarthomea;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hide_ActionBar();
    }

    private void hide_ActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}