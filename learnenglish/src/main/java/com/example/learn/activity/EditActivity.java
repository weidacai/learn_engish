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

    // SearchView list component
    private SearchView searchview;
    // RecyclerView list component
    private RecyclerView recyclerView;
    // The data source of the RecyclerView list component
    private List<WordModel> wordModelList;
    // Data adapter for RecyclerView list component
    private WordEditAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit);
        recyclerView = findViewById(R.id.recyclerview);

        // Set the vertical dividing line of the RecyclerView list component
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        // Set the RecyclerView list component to a linear list
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Query local data
        wordModelList = AppDBHelp.getInstance(this).getWordList();

        // Instantiate the adapter
        adapter = new WordEditAdapter(wordModelList, this);

        // Set the adapter of the RecyclerView list component
        recyclerView.setAdapter(adapter);
        searchview = findViewById(R.id.searchview);

        // Monitor the search action of the soft keyboard of the search component.
        // When the soft keyboard pops up on the phone,
        // click the search key of the soft keyboard to trigger this event
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (wordModelList != null) {
                    // Update the data of the RecyclerView list component
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

        // Publish events subscribed by eventbus, update relevant data
        EventBus.getDefault().post("homeRefresh");

        // Delete the corresponding data
        wordModelList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, wordModelList.size() - position);
    }

}
