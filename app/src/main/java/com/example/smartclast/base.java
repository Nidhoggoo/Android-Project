package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import java.util.HashMap;
import java.util.Map;

import static com.bizideal.smarthome.socket.ControlUtils.getData;

public class base extends Fragment {

    View view;
    static double wd,sd,yw,rq,gz,Co2,qy,Pm25,rthw;
    private TextView tv_wd,tv_sd,tv_yw,tv_rq,tv_gz,tv_Co2,tv_qy,tv_Pm25,tv_rthw;
    static ToggleButton tb_cl,tb_bjd,tb_sd_1,tb_sd_2,tb_fs,tb_mj;
    private EditText et_hwfs;
    private Button btn_hwfs;
    static Map<String,Boolean> map;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.activity_base,null);

        initView();

        initControl1();

        initControl2();

        getdata();

        return view;
    }

    private void initView() {
        tv_wd = view.findViewById(R.id.tv_wd);
        tv_sd = view.findViewById(R.id.tv_sd);
        tv_yw = view.findViewById(R.id.tv_yw);
        tv_rq = view.findViewById(R.id.tv_rq);
        tv_gz = view.findViewById(R.id.tv_gz);
        tv_Co2 = view.findViewById(R.id.tv_Co2);
        tv_qy = view.findViewById(R.id.tv_qy);
        tv_Pm25 = view.findViewById(R.id.tv_Pm25);
        tv_rthw = view.findViewById(R.id.tv_rthw);


        tb_cl = view.findViewById(R.id.tb_cl);
        tb_bjd = view.findViewById(R.id.tb_bjd);
        tb_sd_1 = view.findViewById(R.id.tb_sd_1);
        tb_sd_2 = view.findViewById(R.id.tb_sd_2);
        tb_fs = view.findViewById(R.id.tb_fs);
        tb_mj = view.findViewById(R.id.tb_mj);

        et_hwfs = view.findViewById(R.id.et_hwfs);

        btn_hwfs = view.findViewById(R.id.btn_hwfs);

        map = new HashMap<>();

        initMap();



    }

    private void initMap() {
        map.put("WarningLight",false);
        map.put("Lamp1",false);
        map.put("Lamp2",false);
        map.put("Fan",false);
        map.put("Curtain",false);
        map.put("RFID",false);
        map.put("AIR",false);
        map.put("DVD",false);
        map.put("TV",false);
    }

    private void initControl1() {
        setListener(tb_bjd,"WarningLight");
        setListener(tb_sd_1,"Lamp1");
        setListener(tb_sd_2,"Lamp2");
        setListener(tb_fs,"Fan");
        setListener(tb_cl,"Curtain");
        setListener(tb_mj,"RFID");


    }

    private void initControl2() {
        btn_hwfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_hwfs.getText().toString().equals("1")){
                    control("AIR",1);
                }else if (et_hwfs.getText().toString().equals("2")){
                    control("DVD",1);
                }else if (et_hwfs.getText().toString().equals("3")){
                    control("TV",1);
                }else{
                    Login.show(getActivity(),"1 空调  2 DVD  3 电视");
                }
                et_hwfs.setText("");
            }
        });
    }

    private void setListener(ToggleButton tb, String msg) {
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (tb == tb_sd_1){
                    control("lamp",2);
                }else if (tb == tb_sd_2){
                    control("lamp",3);
                }else {
                    if (b){
                        control(msg,1);
                    }else {
                        if (tb != tb_mj){
                            control(msg,0);
                        }
                    }
                }
                map.put(msg,b);
            }
        });
    }

    private void control(String msg, int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ControlUtils.control(msg,"",i + "");
                if (msg.equals("AIR") || msg.equals("DVD") || msg.equals("TV")){
                    map.put(msg,!map.get(msg));
                }
            }
        }).start();
    }

    private void getdata() {
        ControlUtils.getData();
        SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {
            @Override
            public void onResult(DeviceBean deviceBean) {
                try {
                    wd = Double.parseDouble(deviceBean.getTemperature());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sd = Double.parseDouble(deviceBean.getHumidity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    yw = Double.parseDouble(deviceBean.getSmoke());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    rq = Double.parseDouble(deviceBean.getGas());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    gz = Double.parseDouble(deviceBean.getIllumination());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Co2 = Double.parseDouble(deviceBean.getCo2());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    qy = Double.parseDouble(deviceBean.getAirPressure());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Pm25 = Double.parseDouble(deviceBean.getPM25());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    rthw = Double.parseDouble(deviceBean.getStateHumanInfrared());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_wd.setText(wd + "");
                        tv_sd.setText(sd + "");
                        tv_yw.setText(yw + "");
                        tv_rq.setText(rq + "");
                        tv_gz.setText(gz + "");
                        tv_Co2.setText(Co2 + "");
                        tv_qy.setText(qy + "");
                        tv_Pm25.setText(Pm25 + "");
                        if (rthw != 0){
                            tv_rthw.setText("有人");
                        }else {
                            tv_rthw.setText("无人");
                        }
                    }
                });
            }

        });
    }
}