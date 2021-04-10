package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class draw extends Fragment {
    View view;
    mv mv;

    SQLiteDatabase sqLiteDatabase;
    List<Float> wdlist = new ArrayList<>();
    List<Float> gzlist = new ArrayList<>();

    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.activity_draw,null);

        initView();
        return view;
    }

    private void initView() {
        mv = view.findViewById(R.id.mv);

        sqLiteDatabase = new sql(getActivity()).getWritableDatabase();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (mode.tb_open.isChecked()){
                    wdlist.add((float) base.wd);
                    gzlist.add((float) base.gz);

                    if (wdlist.size() > 4){
                        wdlist.remove(0);
                        gzlist.remove(0);
                    }
                    mv.send(wdlist,gzlist);

                    sqLiteDatabase.execSQL("insert into data values(?,?)",new String[]{wdlist.get(wdlist.size() - 1 ) + "",gzlist.get(gzlist.size() - 1) + ""});
                }
                handler.sendEmptyMessageDelayed(1,1000);
            }
        };
        handler.sendEmptyMessage(1);
    }
}