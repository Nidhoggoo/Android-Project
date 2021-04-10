package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.bizideal.smarthome.socket.ControlUtils;

public class mode extends Fragment {

    View view;
    private RadioButton rb_1,rb_2,rb_3,rb_4,than;
    static ToggleButton tb_open;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.activity_mode,null);

        initView();



        return view;
    }

    private void initView() {
        rb_1 = view.findViewById(R.id.rb_1);
        rb_2 = view.findViewById(R.id.rb_2);
        rb_3 = view.findViewById(R.id.rb_3);
        rb_4 = view.findViewById(R.id.rb_4);

        tb_open = view.findViewById(R.id.tb_open);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (MainActivity.state) {
                    if (tb_open.isChecked()) {
                        if (rb_1.isChecked()) {
                            if (rb_1 != than) {
                                Init_state();
                                than = rb_1;
                            }
                            turn_off(base.tb_sd_1);
                            turn_off(base.tb_sd_2);
                            turn_on(base.tb_cl);
                            if (base.yw > 400){
                                turn_on(base.tb_fs);
                            }else {
                                turn_off(base.tb_fs);
                            }
                        }else if (rb_2.isChecked()){
                            if (rb_2 != than){
                                Init_state();
                                than = rb_2;
                            }
                            turn_off(base.tb_cl);
                            if (base.gz < 200){
                                turn_on(base.tb_sd_1);
                            }
                            else if (base.gz > 500){
                                turn_off(base.tb_sd_1);
                                turn_off(base.tb_sd_2);
                            }

                        } else if(rb_3.isChecked()){
                            if (rb_3 != than){
                                Init_state();
                                than = rb_3;
                            }
                            turn_on("AIR");
                            if (base.map.get("Lamp1")){
                                turn_on(base.tb_sd_2);
                                turn_off(base.tb_sd_1);
                            }else {
                                turn_on(base.tb_sd_1);
                                turn_off(base.tb_sd_2);

                            }
                            linkage.sleep(200);
                        }else if (rb_4.isChecked()){
                            if (rb_4 != than){
                                Init_state();
                                than = rb_4;
                            }
                            if (base.rthw != 0){
                                turn_on(base.tb_bjd);
                                turn_on("Lamp");
                            }else {
                                turn_off(base.tb_bjd);
                                turn_off("Lamp");
                            }
                            //疑惑
                        }else if (than != null){
                            Init_state();
                            than = null;
                        }
                    }
                    linkage.sleep(500);
                }
            }
        }).start();
    }

    public void turn_on(final ToggleButton tb) {
        if (!tb.isChecked()){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tb.setChecked(true);
                }
            });
            linkage.sleep(500);
        }
    }

    private void turn_on(String msg) {

        if (msg.equals("Lamp")){
            //与源码有差异||
            if (!base.map.get("Lamp1") || !base.map.get("Lamp2")){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        base.tb_sd_1.setChecked(true);
                        base.tb_sd_2.setChecked(true);

                    }
                });
            }
        }else {
            if (!base.map.get(msg)){
                base.map.put(msg,true);
                ControlUtils.control(msg,"","1");
            }
        }
        linkage.sleep(500);
    }

    private void turn_off(ToggleButton tb) {
        if (tb.isChecked()){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tb.setChecked(false);
                }
            });
            linkage.sleep(500);
        }
    }

    //此处与源代码不同
    private void turn_off(String msg) {
        if (msg.equals("Lamp")){
            if (base.map.get("Lamp1") || base.map.get("Lamp2")){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        base.tb_sd_1.setChecked(false);
                        base.tb_sd_2.setChecked(false);


                    }
                });

            }
        }else {
            if (base.map.get(msg)) {
                base.map.put(msg, false);
                ControlUtils.control(msg, "", "0");
            }

        }
        linkage.sleep(500);
    }

    private void Init_state() {
        turn_off(base.tb_bjd);
        turn_off(base.tb_sd_1);
        turn_off(base.tb_sd_2);
        turn_off(base.tb_fs);
        turn_off(base.tb_cl);
        turn_off("AIR");
        turn_off("DVD");
        turn_off("TV");
    }
}