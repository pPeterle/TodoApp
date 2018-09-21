package com.example.pedro.todoapp.view.fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.presentation.ViewState;
import com.example.pedro.todoapp.presentation.model.TaskItem;
import com.example.pedro.todoapp.presentation.viewmodel.TasksViewModel;
import com.example.pedro.todoapp.view.adapter.TasksRecyclerAdapter;
import com.fernandocejas.arrow.collections.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

public class TasksFragment extends Fragment {

    private TasksViewModel mViewModel;

    private RecyclerView mRecyclerTasks;
    private FloatingActionButton mFab;

    private TasksRecyclerAdapter mAdapter;

    private List<TaskItem> allTasks = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    public static TasksFragment newInstance() {
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

        mFab.setOnClickListener(fab -> buildAlertDialog());

        setupRecyclerView();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(TasksViewModel.class);

        getLifecycle().addObserver(mViewModel);

        mViewModel.getViewState().observe(this, viewState -> {
            if (viewState != null) {
                handleState(viewState, false);
            }
        });

        mViewModel.getViewStateCompleted().observe(this, viewState -> {
            if (viewState != null) {
                handleState(viewState, true);
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
            allTasks.addAll((allTasks.size()), Lists.reverse(data));
            mAdapter.notifyItemRangeInserted(allTasks.size() - data.size(), data.size());
        } else {
            allTasks.addAll(0, Lists.reverse(data));
            mAdapter.notifyItemRangeInserted(0, data.size());
            mRecyclerTasks.scrollToPosition(0);
        }
    }

    private void setupRecyclerView() {
        mAdapter = new TasksRecyclerAdapter(allTasks);

        mAdapter.setOnItemClickListener(pos -> {
            mViewModel.updateTaskCompleted(allTasks.get(pos).getTask());
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
        builder.setTitle("Add task");
        builder.setView(layout);
        builder.setPositiveButton("Add task", (dialogInterface, i) -> {
            String title = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
            mViewModel.addTodo(title);
            dialogInterface.dismiss();
        });
        builder.create();
        builder.show();
    }
}
