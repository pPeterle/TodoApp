package com.example.pedro.todoapp.view.fragment;


import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.presentation.viewmodel.NoteViewModel;

public class NoteFragment extends Fragment {

    private NoteViewModel mViewModel;

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        // TODO: Use the ViewModel
    }

}
//yourTextView.setMovementMethod(new ScrollingMovementMethod());
