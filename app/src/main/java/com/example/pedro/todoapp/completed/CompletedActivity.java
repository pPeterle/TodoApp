package com.example.pedro.todoapp.completed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.ViewModelFactory;
import com.example.pedro.todoapp.ViewState;
import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CompletedActivity extends AppCompatActivity {

    private static final String TABLE_ID_KEY = "table_id_key";

    @Inject
    ViewModelFactory factory;

    private CompletedViewModel mViewModel;
    private ListView listView;
    private ArrayAdapter<Task> mAdapter;
    private List<Task> mTasksList;

    public static Intent getInstace(Context context, int tableId) {
        Intent intent = new Intent(context, CompletedActivity.class);
        intent.putExtra(TABLE_ID_KEY, tableId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completed);

        int tableId = getIntent().getExtras().getInt(TABLE_ID_KEY);

        listView = findViewById(R.id.completed_listView);
        mTasksList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTasksList);
        listView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this, factory).get(CompletedViewModel.class);
        mViewModel.setTableId(tableId);
        getLifecycle().addObserver(mViewModel);
        mViewModel.getState().observe(this, this::handleState);
    }

    private void handleState(@NonNull ViewState<List<Task>> viewState) {
        switch (viewState.getStatus()) {
            case SUCCESS:
                handleSuccess(viewState.getData());
                break;

            case ERROR:
                handleError(viewState.getError());
                break;

        }
    }

    private void handleError(Throwable error) {
        error.printStackTrace();
        Toast.makeText(this, "um error ocorreu", Toast.LENGTH_SHORT).show();
    }

    private void handleSuccess(List<Task> data) {
        Log.i("test", "os dados: " + data);
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
