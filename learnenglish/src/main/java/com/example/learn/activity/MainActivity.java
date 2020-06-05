package com.example.learn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learn.R;
import com.example.learn.fragment.HomeFragment;
import com.example.learn.fragment.MineFragment;
import com.example.learn.fragment.SearchFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/** The main interface contains two sub-interfaces: HomeFragment, MineFragment*/
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentManager mSupportFragmentManager;
    private Fragment mFragments[];
    private ImageView img[];
    private int res_img_normal[] = {R.drawable.icon_shouye_nor, R.drawable.icon_search_nor, R.drawable.icon_wo_nor};
    private int res_img_selected[] = {R.drawable.icon_shouye_hig, R.drawable.icon_search_hig, R.drawable.icon_wo_hig};
    private TextView tv[];
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initViews();
    }


    private void initViews() {
        mSupportFragmentManager = getSupportFragmentManager();
        mFragments = new Fragment[3];
        mFragments[0] = HomeFragment.newInstance("Home");
        mFragments[1] = SearchFragment.newInstance("Search");
        mFragments[2] = MineFragment.newInstance("Learn");
        mSupportFragmentManager.beginTransaction().add(R.id.framelayout, mFragments[0]).show(mFragments[0]).commit();
        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab1_1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        img = new ImageView[3];
        img[0] = findViewById(R.id.img1);
        img[1] = findViewById(R.id.img1_1);
        img[2] = findViewById(R.id.img2);
        tv = new TextView[3];
        tv[0] = findViewById(R.id.tv1);
        tv[1] = findViewById(R.id.tv1_1);
        tv[2] = findViewById(R.id.tv2);
        EventBus.getDefault().register(this);
        tabSelected(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                tabSelected(0);
                break;
            case R.id.tab1_1:
                tabSelected(1);
                break;
            case R.id.tab2:
                tabSelected(2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void tabSelected(int index) {
        // Set the bottom tab selection state
        for (int i = 0; i < res_img_normal.length; i++) {
            img[i].setImageResource(res_img_normal[i]);
            tv[i].setTextColor(getResources().getColor(R.color.black));
        }
        img[index].setImageResource(res_img_selected[index]);
        tv[index].setTextColor(getResources().getColor(R.color.red));
        showFragment(index);
    }

    private void showFragment(int index) {
        // Dynamically register Fragment
        if (currentTabIndex != index) {
            FragmentTransaction trx = mSupportFragmentManager.beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.framelayout, mFragments[index]);
            }
            trx.show(mFragments[index]).commitAllowingStateLoss();
            currentTabIndex = index;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateSubscriber(String msg) {
        // eventbus event subscription
        if ("homeRefresh".equals(msg)) {
            homeRefresh();
        }
    }

    private void homeRefresh() {
        if (mFragments[0] != null) {
            ((HomeFragment) mFragments[0]).refreshData();
        }
    }

}
