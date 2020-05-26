package com.example.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.R;
import com.example.learn.model.WordModel;
import com.example.learn.util.AppDBHelp;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/** New / edit word view*/
public class AddActivity extends Activity {

    private EditText edit_0, edit_a, edit_b, edit_c, edit_d, edit_answer;
    private int model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add);
        edit_0 = findViewById(R.id.edit_0);
        edit_a = findViewById(R.id.edit_a);
        edit_b = findViewById(R.id.edit_b);
        edit_c = findViewById(R.id.edit_c);
        edit_d = findViewById(R.id.edit_d);
        edit_answer = findViewById(R.id.edit_answer);
        findViewById(R.id.layout_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edit_0.getText().toString())) {
                    if (!TextUtils.isEmpty(edit_a.getText().toString())) {
                        if (!TextUtils.isEmpty(edit_b.getText().toString())) {
                            if (!TextUtils.isEmpty(edit_c.getText().toString())) {
                                if (!TextUtils.isEmpty(edit_d.getText().toString())) {
                                    String answer = edit_answer.getText().toString().replace(" ", "");
                                    if (!TextUtils.isEmpty(answer) && ("A".equals(answer) || "B".equals(answer) || "C".equals(answer) || "D".equals(answer))) {
                                        WordModel wordModel = new WordModel();
                                        wordModel.setEnglish(edit_0.getText().toString());
                                        wordModel.setChinesea(edit_a.getText().toString());
                                        wordModel.setChineseb(edit_b.getText().toString());
                                        wordModel.setChinesec(edit_c.getText().toString());
                                        wordModel.setChinesed(edit_d.getText().toString());
                                        wordModel.setAnswer(answer);
                                        if (model == 0) {
                                            AppDBHelp.getInstance(AddActivity.this).saveWord(wordModel);
                                            // Notify the homepage to update relevant data
                                            EventBus.getDefault().post("homeRefresh");
                                            Toast.makeText(AddActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                        } else if (model == 1) {
                                            AppDBHelp.getInstance(AddActivity.this).updateWord(wordModel);
                                            Toast.makeText(AddActivity.this, "Edit successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    } else {
                                        Toast.makeText(AddActivity.this, "Please enter the correct answer", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AddActivity.this, "Please enter option D", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddActivity.this, "Please enter option C", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddActivity.this, "Please enter option B", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddActivity.this, "Please enter option A", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddActivity.this, "Please enter a word", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // If the data is transmitted, it is edited, otherwise it is added
        Intent intent = getIntent();
        Serializable s = intent.getSerializableExtra("model");
        if (s != null) {
            WordModel wm = (WordModel) s;
            model = 1;
            ((TextView) findViewById(R.id.tv_title)).setText("Edit");
            edit_0.setText(wm.getEnglish());
            edit_a.setText(wm.getChinesea());
            edit_b.setText(wm.getChineseb());
            edit_c.setText(wm.getChinesec());
            edit_d.setText(wm.getChinesed());
            edit_answer.setText(wm.getAnswer());
        }
    }

}
