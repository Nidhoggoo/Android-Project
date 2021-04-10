package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bizideal.smarthome.socket.ControlUtils;

public class linkage extends Fragment {

    View view;
    private Spinner sp_1,sp_2;
    private EditText et_1,et_2;
    private Button btn_start;
    private Switch tb_ld_bjd,tb_ld_fs,tb_ld_sd,tb_ld_mj;
    private TextView tv_ld_time;
    private int num1 = 0,num2 = 0,s = 0, m = 0;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.activity_linkage,null);

        initView();
        return view;
    }

    private void initView() {
        sp_1 = view.findViewById(R.id.sp_1);
        sp_2 = view.findViewById(R.id.sp_2);

        et_1 = view.findViewById(R.id.et_1);
        et_2 = view.findViewById(R.id.et_2);

        btn_start = view.findViewById(R.id.btn_start);

        tb_ld_bjd = view.findViewById(R.id.tb_ld_bjd);
        tb_ld_fs = view.findViewById(R.id.tb_ld_fs);
        tb_ld_sd = view.findViewById(R.id.tb_ld_sd);
        tb_ld_mj = view.findViewById(R.id.tb_ld_mj);


        tv_ld_time = view.findViewById(R.id.tv_ld_time);

        sp_1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,new String[]{"温度","湿度","光照"}));
        sp_2.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,new String[]{">","<="}));


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num2 > 0){
                    btn_start.setText("开始联动");
                    m = 0;
                    s = 0;
                    num2 = 0;
                    tv_ld_time.setText("联动模式还有" + m + "分" + s + "秒");
                }else{
                    try {
                        num1 = Integer.parseInt(et_1.getText().toString());
                        num2 = Integer.parseInt(et_2.getText().toString());
                        m = num2;
                        btn_start.setText("停止联动");
                        handler.sendEmptyMessage(1);
                    } catch (NumberFormatException e) {
                        m = -1;
                        btn_start.setText("开始联动");
                        Login.show(getActivity(),"参数错误");
                    }
                }
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                tv_ld_time.setText("联动模式还有" + m + "分" + s + "秒");
                if (m > 0 && s == 0){
                    s= 59;
                    m--;
                }else if (m== 0 && s== 0){
                    num2 = 0;
                    btn_start.setText("开始联动");
                }else {
                    s--;
                }

                if(num2 > 0){
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            }

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("!2344");
                    if (num2 > 0){
                        if (sp_2.getSelectedItem().toString().equals(">")){
                            if (sp_1.getSelectedItem().toString().equals("温度")){
                                if (base.wd > num1){
                                    ju1();
                                }else {
                                    ju2();
                                }
                            }else if (sp_1.getSelectedItem().toString().equals("湿度")){
                                if (base.sd > num1){
                                    ju1();
                                }else {
                                    ju2();
                                }
                            } else{
                                if (base.gz > num1){
                                    ju1();
                                }else {
                                    ju2();
                                }
                            }
                        } else {
                            if (sp_1.getSelectedItem().toString().equals("温度")){
                                if (base.wd <= num1){
                                    ju1();
                                }else {
                                    ju2();
                                }
                            } else if (sp_1.getSelectedItem().toString().equals("湿度")){
                                if (base.sd <= num1){
                                    ju1();
                                }else {
                                    ju2();
                                }
                            } else {
                                if (base.gz <= num1){
                                    ju1();
                                }else {
                                    ju2();
                                }
                            }
                        }
                    }else if (num2 == 0){
                        num2--;
                        Init_state();
                    }
                    sleep(1000);
                }
            }
        }).start();
    }



    private void ju1() {
        if (tb_ld_bjd.isChecked()){
            turn_on(base.tb_bjd);
        }else {
            turn_off(base.tb_bjd);
        }
        if (tb_ld_fs.isChecked()){
            turn_on(base.tb_fs);
        }else {
            turn_off(base.tb_fs);
        }
        if (tb_ld_sd.isChecked()){
            turn_on(base.tb_sd_1);
            turn_on(base.tb_sd_2);
        }else {
            turn_off(base.tb_sd_1);
            turn_off(base.tb_sd_2);
        }
        if (tb_ld_bjd.isChecked()){
            turn_on(base.tb_bjd);
        }else {
            turn_off(base.tb_bjd);
        }
        if (!base.map.get("RFID")) {
            if (tb_ld_mj.isChecked()) {
                turn_off(base.tb_mj);
            } else {
                turn_off(base.tb_mj);
            }
        }
    }

    private void ju2() {
        if (tb_ld_bjd.isChecked()){
            turn_off(base.tb_bjd);
        }else {
            turn_off(base.tb_bjd);
        }
        if (tb_ld_fs.isChecked()){
            turn_off(base.tb_fs);
        }else {
            turn_off(base.tb_fs);
        }
        if (tb_ld_sd.isChecked()){
            turn_off(base.tb_sd_1);
            turn_off(base.tb_sd_2);
        }else {
            turn_off(base.tb_sd_1);
            turn_off(base.tb_sd_2);
        }
        if (tb_ld_bjd.isChecked()){
            turn_off(base.tb_bjd);
        }else {
            turn_off(base.tb_bjd);
        }
        if (!base.map.get("RFID")) {
            if (tb_ld_mj.isChecked()) {
                turn_off(base.tb_mj);
            } else {
                turn_off(base.tb_mj);
            }
        }
    }

    private void turn_on(ToggleButton tb) {
        if (!tb.isChecked()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tb.setChecked(true);
                }
            });
            sleep(500);
        }
    }

    private void turn_off(ToggleButton tb) {
        if (tb.isChecked()){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tb.setChecked(false);
                }
            });
            sleep(500);
        }
    }

    private void turn_off(String msg) {
        if (base.map.get(msg)){
            ControlUtils.control(msg,"","0");
            base.map.put(msg,false);
            sleep(500);
        }
    }



    private void Init_state() {


        turn_off(base.tb_bjd);
        turn_off(base.tb_fs);
        turn_off(base.tb_sd_1);
        turn_off(base.tb_sd_2);
        turn_off(base.tb_cl);
        if (tb_ld_mj.isChecked()){
            turn_off(base.tb_mj);
            turn_on(base.tb_mj);
        }
        turn_off("AIR");
        turn_off("DVD");
        turn_off("TV");
    }

    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}