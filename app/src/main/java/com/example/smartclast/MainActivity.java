package com.example.smartclast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{

    private static final String TAG = "MainActivity";
    public static ViewPager vp_Main;
    private TextView tv_time2;
    List<Fragment> list;
    ActionBar actionBar;
    Handler handler;

    //判断Fragment是否启动，防止线程错误，后面几个类会调用
    static boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置ActionBar
        initActionBar();

        //绑定控件
        initView();

        //设置右下角时间
        initTime();

        //设置5个界面滑动和切换
        initViewPager();

        //连接服务器
        Connect("192.168.10.2");
    }




    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("智能家居");
        actionBar.setIcon(R.drawable.robot);
    }

    private void initView() {
        vp_Main = findViewById(R.id.vp_Main);
        tv_time2 = findViewById(R.id.tv_time2);
    }

    //连接服务器，调用smarthome包
    private void Connect(String ip) {
        ControlUtils.setUser("bizideal","123456",ip);
        SocketClient.getInstance().creatConnect();
        SocketClient.getInstance().login(new LoginCallback() {
            @Override
            public void onEvent(String s) {
                if (s.equals(ConstantUtil.SUCCESS)) {
                    Login.show(MainActivity.this, "连接成功");
                }else {
                    Login.show(MainActivity.this,"连接失败");
                }
            }
        });
    }

    private void initTime() {
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                tv_time2.setText(new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss").format(new Date()));
                handler.sendEmptyMessageDelayed(1,1000);
            }
        };
        handler.sendEmptyMessage(1);
    }


    private void initViewPager() {
        list = new ArrayList<>();

        //把5个fragment放到集合中
        list.add(new choice());
        list.add(new base());
        list.add(new linkage());
        list.add(new mode());
        list.add(new draw());

        //先设置actionBar，最底下并implements ActionBar.TabListener，实现3个方法，为actionBar设置跳转
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("选择").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("基本").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("联动").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("模式").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("绘图").setTabListener(this));

        //设置ViewPager,添加5个界面
        vp_Main.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        //设置choice为默认界面，并把界面分层
        vp_Main.setCurrentItem(0);
        vp_Main.setOffscreenPageLimit(4);
        vp_Main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                //设置base界面时间字体颜色，详细看任务书
                changColor();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //设置base界面时间字体颜色，详细看任务书
    private void changColor() {
        if (vp_Main.getCurrentItem() == 1){
            tv_time2.setTextColor(Color.WHITE);
        }else {
            tv_time2.setTextColor(Color.BLACK);
        }
    }

    //设置actionBar是其能与ViewPager同步
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        vp_Main.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        state = false;
    }
}