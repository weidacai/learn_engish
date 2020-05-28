package com.example.learn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.learn.R;
import com.example.learn.activity.EditActivity;
import com.example.learn.model.WordModel;
import com.example.learn.util.AppDBHelp;

import java.util.List;

// search view
public class SearchFragment extends Fragment {

    private View view;

    public static SearchFragment newInstance(String text) {
        SearchFragment fragmentCommon = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_search, container, false);
        final TextView tv_english = view.findViewById(R.id.tv_english);
        final TextView tv_0 = view.findViewById(R.id.tv_0);
        SearchView searchview = view.findViewById(R.id.searchview);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<WordModel> wordModelList = AppDBHelp.getInstance(getActivity()).findWordList(query);
                if (wordModelList != null && wordModelList.size() == 1) {
                    WordModel wordModel = wordModelList.get(0);
                    tv_english.setText(wordModel.getEnglish());
                    if("A".equals(wordModel.getAnswer())){
                        tv_0.setText(wordModel.getChinesea());
                    }else if("B".equals(wordModel.getAnswer())){
                        tv_0.setText(wordModel.getChineseb());
                    }else if("C".equals(wordModel.getAnswer())){
                        tv_0.setText(wordModel.getChinesec());
                    }else if("D".equals(wordModel.getAnswer())){
                        tv_0.setText(wordModel.getChinesed());
                    }
                }else{
                    tv_english.setText("");
                    tv_0.setText("Without this word, please make sure the word is spelled correctly");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }


}
