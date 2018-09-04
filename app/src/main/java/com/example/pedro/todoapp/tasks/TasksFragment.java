package com.example.pedro.todoapp.tasks;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.Utils.ListUtils;
import com.example.pedro.todoapp.ViewModelFactory;
import com.example.pedro.todoapp.ViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TasksFragment extends Fragment {

    private TasksViewModel mViewModel;

    private RecyclerView mRecyclerTasks;
    private FloatingActionButton mFab;

    private TasksRecyclerAdapter mAdapter;

    private List<TaskItem> allTasks = new ArrayList<>();

    @Inject
    ViewModelFactory mViewModelFactory;

    public static TasksFragment newInstance() {
        Log.d("test", "viewmodel chamado 2");
        return new TasksFragment();
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

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertDialog();
            }
        });

        setupRecyclerView();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(TasksViewModel.class);

        getLifecycle().addObserver(mViewModel);

        mViewModel.getViewState().observe(this, new Observer<ViewState<List<TaskItem>>>() {
            @Override
            public void onChanged(@Nullable ViewState<List<TaskItem>> viewState) {
                if (viewState != null) {
                    handleState(viewState, false);
                }
            }
        });

        mViewModel.getViewStateCompleted().observe(this, new Observer<ViewState<List<TaskItem>>>() {
            @Override
            public void onChanged(@Nullable ViewState<List<TaskItem>> viewState) {
                if (viewState != null) {
                    handleState(viewState, true);
                }
            }
        });
    }

    private void handleState(@NonNull ViewState<List<TaskItem>> viewState, boolean completed) {
        switch (viewState.getStatus()) {
            case SUCCESS:
                handleSuccess(viewState.getData(), completed);
                break;

            case ERROR:
                handleError(viewState.getError());
        }
    }

    private void handleError(@NonNull Throwable error) {
        error.printStackTrace();
        Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_SHORT);
    }

    private void handleSuccess(List<TaskItem> data, boolean completed) {
        if (completed) {
            allTasks.addAll((allTasks.size()), ListUtils.inverterOrder(data));
            mAdapter.notifyItemRangeInserted(allTasks.size() - data.size(), data.size());
        } else {
            allTasks.addAll(0, ListUtils.inverterOrder(data));
            mAdapter.notifyItemRangeInserted(0, data.size());
            mRecyclerTasks.scrollToPosition(0);
        }
    }

    private void setupRecyclerView() {
        mAdapter = new TasksRecyclerAdapter(allTasks);

        mAdapter.setOnItemClickListener(new TasksRecyclerAdapter.OnTaskCompletedListener() {
            @Override
            public void onTaskCompleted(int pos) {
                mViewModel.updateTaskCompleted(allTasks.get(pos));
                allTasks.remove(pos);
                mAdapter.notifyItemRemoved(pos);
            }
        });

        mRecyclerTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerTasks.setAdapter(mAdapter);
    }

    private void buildAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View layout = getLayoutInflater().inflate(R.layout.add_task, null);
        final TextInputLayout textInputLayout = layout.findViewById(R.id.add_task_title);
        builder.setTitle("Add task");
        builder.setView(layout);
        builder.setPositiveButton("Add task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = textInputLayout.getEditText().getText().toString();
                mViewModel.addTodo(title);
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
}
