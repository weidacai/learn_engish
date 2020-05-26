package com.example.learn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learn.R;
import com.example.learn.adapter.WordEditAdapter;
import com.example.learn.listener.WordEditClickLinstener;
import com.example.learn.model.WordModel;
import com.example.learn.util.AppDBHelp;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**Modify word view */
public class EditActivity extends Activity implements WordEditClickLinstener {


    private SearchView searchview;
    private RecyclerView recyclerView;
    private List<WordModel> wordModelList;
    private WordEditAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wordModelList = AppDBHelp.getInstance(this).getWordList();
        adapter = new WordEditAdapter(wordModelList, this);
        recyclerView.setAdapter(adapter);
        searchview = findViewById(R.id.searchview);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (wordModelList != null) {
                    wordModelList.clear();
                    wordModelList.addAll(AppDBHelp.getInstance(EditActivity.this).findWordList(query));
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void edit(int position) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("model", wordModelList.get(position));
        startActivity(intent);
    }

    @Override
    public void delete(int position) {
        AppDBHelp.getInstance(this).deleteWord(wordModelList.get(position).getId());
        // Notify the homepage to update relevant data
        EventBus.getDefault().post("homeRefresh");
        wordModelList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, wordModelList.size() - position);
    }

}
