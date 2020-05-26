package com.example.learn.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.learn.R;
import com.example.learn.activity.AddActivity;
import com.example.learn.activity.EditActivity;
import com.example.learn.activity.EditPwdActivity;
import com.example.learn.activity.LoginActivity;
import com.example.learn.util.AppDBHelp;
import com.example.learn.util.SPHelper;

// My view. Contents include: add words; modify words; log out
public class MineFragment extends Fragment implements View.OnClickListener {

    private View view;


    public static MineFragment newInstance(String text) {
        MineFragment fragmentCommon = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mine, container, false);
        initView();
        return view;
    }

    private void initView() {
        view.findViewById(R.id.layout_add).setOnClickListener(this);
        view.findViewById(R.id.layout_edit).setOnClickListener(this);
        view.findViewById(R.id.layout_editpwd).setOnClickListener(this);
        view.findViewById(R.id.layout_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_add:
                startActivity(new Intent(getActivity(), AddActivity.class));
                break;
            case R.id.layout_edit:
                startActivity(new Intent(getActivity(), EditActivity.class));
                break;
            case R.id.layout_editpwd:
                startActivity(new Intent(getActivity(), EditPwdActivity.class));
                break;
            case R.id.layout_logout:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                SPHelper.getInstance(getActivity()).setRemember(0);
                getActivity().finish();
                break;
            default:
                break;
        }
    }

}
