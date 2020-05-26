package com.example.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.learn.R;
import com.example.learn.util.SPHelper;

import java.util.Random;

/** Start splash screen view*/
public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        int random = new Random().nextInt(10);
        findViewById(R.id.img).setBackgroundResource(R.drawable.splash0 + random);

        // stop 2s
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SPHelper spHelper = SPHelper.getInstance(SplashActivity.this);
                            String[] userinfo = spHelper.getUserInfo();
                            if (userinfo != null && userinfo.length == 2 && !TextUtils.isEmpty(userinfo[0]) && !TextUtils.isEmpty(userinfo[1]) && spHelper.getRemember() == 1) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        }).start();
    }

}
