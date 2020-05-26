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

/** Registration view*/
public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText edit_username, edit_password, edit_password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg);
        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        edit_password2 = findViewById(R.id.edit_password2);
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.tv_reg).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                String username = edit_username.getText().toString();
                String password = edit_password.getText().toString();
                String password2 = edit_password2.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    if (!TextUtils.isEmpty(password)) {
                        if (!TextUtils.isEmpty(password2) && password.equals(password2)) {
                            Toast.makeText(this, "Register successfully, please log in", Toast.LENGTH_SHORT).show();
                            SPHelper.getInstance(this).saveUserInfo(username, password);
                            startActivity(new Intent(this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Two password input is inconsistent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "password can not be blank", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Account cannot be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_reg:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

}