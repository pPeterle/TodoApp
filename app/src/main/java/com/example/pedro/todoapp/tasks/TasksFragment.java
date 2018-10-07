package com.example.pedro.todoapp.tasks;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pedro.todoapp.data.entity.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.ViewState;
import com.fernandocejas.arrow.collections.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TasksFragment extends Fragment {

    private static final String TABLE_ID_KEY = "table_id_key";

    private TasksViewModel mViewModel;

    private RecyclerView mRecyclerTasks;
    private FloatingActionButton mFab;

    private TasksRecyclerAdapter mAdapter;

    private List<Task> allTasks = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    public static TasksFragment newInstance(int tableId) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(TABLE_ID_KEY, tableId);
        fragment.setArguments(args);
        Log.i("test", "fragment criado com tableId " + tableId);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        mRecyclerTasks = view.findViewById(R.id.todo_recyclerTasks);
        mFab = view.findViewById(R.id.tasks_fab);

        mFab.setOnClickListener(fab -> buildAlertDialog());

        setupRecyclerView();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int tableId = getArguments().getInt(TABLE_ID_KEY);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TasksViewModel.class);
        mViewModel.setTableId(tableId);
        getLifecycle().addObserver(mViewModel);

        mViewModel.getViewState().observe(this, viewState -> {
            if (viewState != null) {
                handleState(viewState);
            }
        });
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

    private void handleSuccess(List<Task> data) {
        allTasks.clear();
        allTasks.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    private void handleError(@NonNull Throwable error) {
        error.printStackTrace();
        Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_SHORT);
    }

    private void setupRecyclerView() {
        mAdapter = new TasksRecyclerAdapter(allTasks);

        mAdapter.setOnItemClickListener(pos -> {
            mViewModel.updateTaskCompleted(allTasks.get(pos));
            allTasks.remove(pos);
            mAdapter.notifyItemRemoved(pos);
        });

        mRecyclerTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerTasks.setAdapter(mAdapter);
    }

    private void buildAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View layout = getLayoutInflater().inflate(R.layout.add_task, null);
        final TextInputLayout textInputLayout = layout.findViewById(R.id.add_task_title);

        builder.setTitle("Add task")
                .setView(layout)
                .setPositiveButton("Add task", (dialogInterface, i) -> {
                    String title = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
                    mViewModel.addTodo(title);
                    dialogInterface.dismiss();
                })
                .create()
                .show();
    }
}
