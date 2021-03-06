package com.wxgh.livehappy;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wxgh.livehappy.fragment.Tab01;
import com.wxgh.livehappy.fragment.Tab02;
import com.wxgh.livehappy.fragment.UserCenterFragment;
import com.wxgh.livehappy.model.Users;
import com.wxgh.livehappy.utils.StaticManger;
import com.wxgh.livehappy.utils.Verification;

import cn.jpush.android.api.JPushInterface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fm;

    private Tab01 t1;
    private Tab02 t2;
    private UserCenterFragment userCenterFragment;

    private ImageButton btn_tab_bottom1;
    private ImageButton btn_tab_bottom2;
    private ImageButton btn_tab_bottom3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initData();
        initfragment();
        onclick();
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.main_framelayout, t1, "F1").commit();
    }

    /**
     * 加载控件
     */
    public void initData() {
        btn_tab_bottom1 = (ImageButton) findViewById(R.id.btn_tab_bottom1);
        btn_tab_bottom2 = (ImageButton) findViewById(R.id.btn_tab_bottom2);
        btn_tab_bottom3 = (ImageButton) findViewById(R.id.btn_tab_bottom3);
    }

    /**
     * 实例化fragment布局
     */
    public void initfragment() {
        t1 = new Tab01();
        t1.setmContext(this);
        t2 = new Tab02();
        t2.mainActivity = this;
        userCenterFragment = new UserCenterFragment();

    }


    /**
     * 注册单击监听
     */
    public void onclick() {
        btn_tab_bottom1.setOnClickListener(this);
        btn_tab_bottom2.setOnClickListener(this);
        btn_tab_bottom3.setOnClickListener(this);
    }

    /**
     * 单击监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        reset();
        initfragment();
        switch (v.getId()){
            case R.id.btn_tab_bottom1:
                btn_tab_bottom1.setImageResource(R.drawable.homepage_h);
                fm.beginTransaction().replace(R.id.main_framelayout, t1, "F1").commit();
                break;
            case R.id.btn_tab_bottom2:
                fm.beginTransaction().replace(R.id.main_framelayout, t2, "F2").commit();
                btn_tab_bottom2.setImageResource(R.drawable.live_h);
                break;
            case R.id.btn_tab_bottom3:
                fm.beginTransaction().replace(R.id.main_framelayout, userCenterFragment, "F3").commit();
                btn_tab_bottom3.setImageResource(R.drawable.me_h);
                break;
        }
    }

    public void tab_bottom1(View view) {
        reset();

        fm.beginTransaction().replace(R.id.main_framelayout, t1, "F1").commit();
        btn_tab_bottom1.setImageResource(R.drawable.homepage_h);
    }

    public void tab_bottom2(View view) {
        reset();
        fm.beginTransaction().replace(R.id.main_framelayout, t2, "F2").commit();
        btn_tab_bottom2.setImageResource(R.drawable.live_h);
    }

    public void tab_bottom3(View view) {
        reset();
        fm.beginTransaction().replace(R.id.main_framelayout, userCenterFragment, "F3").commit();
        btn_tab_bottom3.setImageResource(R.drawable.me_h);
    }


    /**
     * 重置事件
     */
    public void reset() {
        btn_tab_bottom1.setImageResource(R.drawable.homepage);
        btn_tab_bottom2.setImageResource(R.drawable.live);
        btn_tab_bottom3.setImageResource(R.drawable.me);
    }

    /**
     * 设置当前显示的是第三个fragment
     */
    public void setCurrentFragment1() {
        reset();
        initfragment();
        btn_tab_bottom1.setImageResource(R.drawable.homepage_h);
        fm.beginTransaction().replace(R.id.main_framelayout, t1, "F1").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        Users user = StaticManger.getCurrentUser(MainActivity.this);
        if (user!=null){
            Verification.updateUserOnline(user);
        }
        super.onDestroy();
    }
}



