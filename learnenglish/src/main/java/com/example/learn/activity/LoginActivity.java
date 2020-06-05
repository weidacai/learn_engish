package com.example.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learn.R;
import com.example.learn.util.SPHelper;

/**Login view */
public class LoginActivity extends Activity implements View.OnClickListener {


    private String[] userinfo;
    private EditText edit_username, edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.tv_reg).setOnClickListener(this);

        // Get account information saved locally
        userinfo = SPHelper.getInstance(this).getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                String username = edit_username.getText().toString();
                String password = edit_password.getText().toString();
                if (!TextUtils.isEmpty(username) && username.equals(userinfo[0])) {
                    if (!TextUtils.isEmpty(password) && password.equals(userinfo[1])) {
                        // Remember the password after successful login, will automatically log in later
                        SPHelper.getInstance(this).setRemember(1);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Wrong account", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_reg:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

}