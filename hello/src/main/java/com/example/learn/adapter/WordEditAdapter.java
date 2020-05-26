package com.example.learn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learn.R;
import com.example.learn.listener.WordEditClickLinstener;
import com.example.learn.model.WordModel;

import java.util.List;

public class WordEditAdapter extends RecyclerView.Adapter<WordEditAdapter.ViewHolder> {


    private List<WordModel> wordModelList;
    private WordEditClickLinstener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_english, tv_edit, tv_delete;

        public ViewHolder(View view) {
            super(view);
            tv_english = view.findViewById(R.id.tv_english);
            tv_edit = view.findViewById(R.id.tv_edit);
            tv_delete = view.findViewById(R.id.tv_delete);
        }
    }

    public WordEditAdapter(List<WordModel> wordModelList, WordEditClickLinstener linstener) {
        this.wordModelList = wordModelList;
        this.listener = linstener;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_edit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        WordModel wordModel = wordModelList.get(position);
        holder.tv_english.setText(wordModel.getEnglish());
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.edit(position);
                }
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.delete(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordModelList.size();
    }


}
