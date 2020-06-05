package com.example.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learn.R;
import com.example.learn.util.AppDBHelp;
import com.example.learn.util.SPHelper;

import java.util.Random;

/**Change password view*/
public class EditPwdActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editpwd);
        final EditText edit_password = findViewById(R.id.edit_password);
        final EditText edit_password1 = findViewById(R.id.edit_password1);
        final EditText edit_password2 = findViewById(R.id.edit_password2);
        findViewById(R.id.layout_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = SPHelper.getInstance(EditPwdActivity.this).getUserInfo()[1];

                // Whether the input data is empty and equal
                if (!TextUtils.isEmpty(edit_password.getText().toString()) && edit_password.getText().toString().equals(old_password)) {
                    if (!TextUtils.isEmpty(edit_password1.getText().toString()) && !TextUtils.isEmpty(edit_password2.getText().toString()) && edit_password1.getText().toString().equals(edit_password2.getText().toString())) {
                        SPHelper.getInstance(EditPwdActivity.this).saveUserInfo(SPHelper.getInstance(EditPwdActivity.this).getUserInfo()[0], edit_password1.getText().toString());
                        Toast.makeText(EditPwdActivity.this, "Successfully modified", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditPwdActivity.this, "Two passwords are inconsistent", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditPwdActivity.this, "The original password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
