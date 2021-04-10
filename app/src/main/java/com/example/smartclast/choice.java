package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class choice extends Fragment {

    private ImageView iv_robot;
    private TextView tv_c_base,tv_c_mode,tv_c_linkage,tv_c_draw;
    View view;
    private Map<TextView,Boolean> map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.activity_choice,null);

        initView();

        setListener(tv_c_base,1);
        setListener(tv_c_linkage,2);
        setListener(tv_c_mode,3);
        setListener(tv_c_draw,4);

        return view;
    }


    private void initView() {
        tv_c_base = view.findViewById(R.id.tv_c_base);
        tv_c_mode = view.findViewById(R.id.tv_c_mode);
        tv_c_linkage = view.findViewById(R.id.tv_c_linkage);
        tv_c_draw = view.findViewById(R.id.tv_c_draw);

        iv_robot = view.findViewById(R.id.iv_robot);

        map = new HashMap<>();

        map.put(tv_c_base,true);
        map.put(tv_c_mode,false);
        map.put(tv_c_linkage,false);
        map.put(tv_c_draw,false);
    }



    private void setListener(TextView tv, int i) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map.get(tv)){
                    MainActivity.vp_Main.setCurrentItem(i);
                }else {
                    map.put(tv_c_base,false);
                    map.put(tv_c_mode,false);
                    map.put(tv_c_linkage,false);
                    map.put(tv_c_draw,false);

                    map.put(tv,true);

                    ObjectAnimator animator = new ObjectAnimator().ofFloat(iv_robot,"TranslationY",tv.getY() - iv_robot.getHeight() - 80).setDuration(800);
                    animator.setRepeatCount(0);
                    animator.start();
                }
            }
        });

    }
}