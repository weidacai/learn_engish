package com.example.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.R;
import com.example.learn.model.WordModel;
import com.example.learn.util.AppDBHelp;

import java.util.List;

public class LearnActivity extends Activity implements View.OnClickListener {


    private List<WordModel> wordModelList;
    private TextView tv_english, tv_tips;
    private RadioGroup radiogroup;
    private RadioButton radio1, radio2, radio3, radio4;
    private AppDBHelp appDBHelp;
    private int index;
    private WordModel wordModel;
    private String userAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_learn);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type > 0) {
            findViewById(R.id.layout_back).setOnClickListener(this);
            findViewById(R.id.btn_pre).setOnClickListener(this);
            findViewById(R.id.btn_ok).setOnClickListener(this);
            findViewById(R.id.btn_next).setOnClickListener(this);
            tv_english = findViewById(R.id.tv_english);
            tv_tips = findViewById(R.id.tv_tips);
            radiogroup = findViewById(R.id.radiogroup);
            radio1 = findViewById(R.id.radio1);
            radio2 = findViewById(R.id.radio2);
            radio3 = findViewById(R.id.radio3);
            radio4 = findViewById(R.id.radio4);
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    // The user selects an answer
                    switch (checkedId) {
                        case R.id.radio1:
                            userAnswer = "A";
                            break;
                        case R.id.radio2:
                            userAnswer = "B";
                            break;
                        case R.id.radio3:
                            userAnswer = "C";
                            break;
                        case R.id.radio4:
                            userAnswer = "D";
                            break;
                        default:
                            break;
                    }
                }
            });
            appDBHelp = AppDBHelp.getInstance(this);

            // Get different types of question bank from local database
            switch (type) {
                case 1:
                    wordModelList = appDBHelp.getTodayWordList();
                    break;
                case 2:
                    wordModelList = appDBHelp.getLearnedWordList();
                    break;
                case 3:
                    wordModelList = appDBHelp.getWordList();
                    break;
                case 4:
                    wordModelList = appDBHelp.getWrongWordList();
                    break;
                default:
                    break;
            }
            loadQuestion();
        } else {
            Toast.makeText(this, "Parameter error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.btn_pre:
                // click on the previous question
                if (index == 0) {
                    Toast.makeText(this, "Already the first question", Toast.LENGTH_SHORT).show();
                } else {
                    index--;
                    loadQuestion();
                }
                break;
            case R.id.btn_ok:
                if (TextUtils.isEmpty(userAnswer)) {
                    Toast.makeText(this, "Please select the correct answer", Toast.LENGTH_SHORT).show();
                } else if (wordModel.getAnswer().contains(userAnswer)) {
                    // Answer the correct prompt message
                    tv_tips.setVisibility(View.VISIBLE);
                    if (wordModel.getAnswer().contains("A")) {
                        tv_tips.setText("correct answer。" + wordModel.getEnglish() + ": " + wordModel.getChinesea());
                    } else if (wordModel.getAnswer().contains("B")) {
                        tv_tips.setText("correct answer。" + wordModel.getEnglish() + ": " + wordModel.getChineseb());
                    } else if (wordModel.getAnswer().contains("C")) {
                        tv_tips.setText("correct answer。" + wordModel.getEnglish() + ": " + wordModel.getChinesec());
                    } else if (wordModel.getAnswer().contains("D")) {
                        tv_tips.setText("correct answer。" + wordModel.getEnglish() + ": " + wordModel.getChinesed());
                    }
                    tv_tips.setTextColor(getResources().getColor(R.color.green));
                    appDBHelp.learnWord(wordModel.getId(), 1);
                } else {
                    // Answer the error message
                    tv_tips.setVisibility(View.VISIBLE);
                    if (wordModel.getAnswer().contains("A")) {
                        tv_tips.setText("wrong answer。" + wordModel.getEnglish() + ": " + wordModel.getChinesea());
                    } else if (wordModel.getAnswer().contains("B")) {
                        tv_tips.setText("wrong answer。" + wordModel.getEnglish() + ": " + wordModel.getChineseb());
                    } else if (wordModel.getAnswer().contains("C")) {
                        tv_tips.setText("wrong answer。" + wordModel.getEnglish() + ": " + wordModel.getChinesec());
                    } else if (wordModel.getAnswer().contains("D")) {
                        tv_tips.setText("wrong answer。" + wordModel.getEnglish() + ": " + wordModel.getChinesed());
                    }
                    tv_tips.setTextColor(getResources().getColor(R.color.red));
                    appDBHelp.learnWord(wordModel.getId(), 0);
                }
                break;
            case R.id.btn_next:
                // Click on the next question
                if (index == wordModelList.size() - 1) {
                    Toast.makeText(this, "Already the last question", Toast.LENGTH_SHORT).show();
                } else {
                    index++;
                    loadQuestion();
                }
                break;
        }
    }

    private void loadQuestion() {
        radiogroup.clearCheck();
        wordModel = wordModelList.get(index);
        tv_english.setText(wordModel.getEnglish());
        radio1.setText("A " + wordModel.getChinesea());
        radio2.setText("B " + wordModel.getChineseb());
        radio3.setText("C " + wordModel.getChinesec());
        radio4.setText("D " + wordModel.getChinesed());
        tv_tips.setVisibility(View.INVISIBLE);
    }


}
