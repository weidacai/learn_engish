package com.example.learn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.learn.R;
import com.example.learn.activity.LearnActivity;
import com.example.learn.model.WordModel;
import com.example.learn.util.AppDBHelp;
import com.example.learn.util.SPHelper;

import java.util.List;

// Home view.
public class HomeFragment extends Fragment implements View.OnClickListener {

   private View view;
   private TextView tv_num_today, tv_num_total, tv_total, tv_wrong;
   private LinearLayout layout_today, layout_total, layout_total2, layout_wrong;
   private AppDBHelp appDBHelp;
   private List<WordModel> wordModelList0, wordModelList1, wordModelList, wordModelList2;

   public static HomeFragment newInstance(String text) {
      HomeFragment fragmentCommon = new HomeFragment();
      Bundle bundle = new Bundle();
      bundle.putString("text", text);
      fragmentCommon.setArguments(bundle);
      return fragmentCommon;
   }


   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.frag_main, container, false);
      initView();
      return view;
   }

   private void initView() {
      tv_num_today = view.findViewById(R.id.tv_num_today);
      tv_num_total = view.findViewById(R.id.tv_num_total);
      tv_total = view.findViewById(R.id.tv_total);
      tv_wrong = view.findViewById(R.id.tv_wrong);
      layout_today = view.findViewById(R.id.layout_today);
      layout_today.setOnClickListener(this);
      layout_total = view.findViewById(R.id.layout_total);
      layout_total.setOnClickListener(this);
      layout_total2 = view.findViewById(R.id.layout_total2);
      layout_total2.setOnClickListener(this);
      layout_wrong = view.findViewById(R.id.layout_wrong);
      layout_wrong.setOnClickListener(this);
      TextView tv_username = view.findViewById(R.id.tv_username);
      tv_username.setText("Welcome " + SPHelper.getInstance(getActivity()).getUserInfo()[0]);
      appDBHelp = AppDBHelp.getInstance(getActivity());
      refreshData();
   }

   public void refreshData() {

      // Get all the data of this page from the local database
      wordModelList0 = appDBHelp.getTodayWordList();
      wordModelList1 = appDBHelp.getLearnedWordList();
      wordModelList = appDBHelp.getWordList();
      wordModelList2 = appDBHelp.getWrongWordList();
      if (wordModelList != null) {
         tv_total.setText(wordModelList.size() + "");
      }
      if (wordModelList1 != null) {
         tv_num_total.setText(wordModelList1.size() + "");
      }
      if (wordModelList0 != null) {
         tv_num_today.setText(wordModelList0.size() + "");
      }
      if (wordModelList2 != null) {
         tv_wrong.setText(wordModelList2.size() + "");
      }
   }

   // Click event
   @Override
   public void onClick(View v) {
      Intent intent;
      switch (v.getId()) {
         case R.id.layout_today:
            if (wordModelList0 != null && wordModelList0.size() > 0) {
               intent = new Intent(getActivity(), LearnActivity.class);
               intent.putExtra("type", 1);
               startActivity(intent);
            }
            break;
         case R.id.layout_total:
            if (wordModelList1 != null && wordModelList1.size() > 0) {
               intent = new Intent(getActivity(), LearnActivity.class);
               intent.putExtra("type", 2);
               startActivity(intent);
            }
            break;
         case R.id.layout_total2:
            if (wordModelList != null && wordModelList.size() > 0) {
               intent = new Intent(getActivity(), LearnActivity.class);
               intent.putExtra("type", 3);
               startActivity(intent);
            }
            break;
         case R.id.layout_wrong:
            if (wordModelList2 != null && wordModelList2.size() > 0) {
               intent = new Intent(getActivity(), LearnActivity.class);
               intent.putExtra("type", 4);
               startActivity(intent);
            }
            break;
         default:
            break;
      }
   }

}
