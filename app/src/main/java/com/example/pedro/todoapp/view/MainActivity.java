package com.example.pedro.todoapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.view.fragment.HomeFragment;
import com.example.pedro.todoapp.view.fragment.ListFragment;
import com.example.pedro.todoapp.view.fragment.NoteFragment;
import com.example.pedro.todoapp.view.fragment.ReminderFragment;
import com.example.pedro.todoapp.view.fragment.TasksFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;

public class MainActivity extends AppCompatActivity {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private HomeFragment homeFragment = HomeFragment.newInstance();
    private TasksFragment tasksFragment = TasksFragment.newInstance();
    private ReminderFragment reminderFragment = ReminderFragment.newInstance();
    private ListFragment listFragment = ListFragment.newInstance();
    private NoteFragment noteFragment = NoteFragment.newInstance();

    private Fragment actualFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(homeFragment);
                    return true;
                case R.id.navigation_todo:
                    switchFragment(tasksFragment);
                    return true;
                case R.id.navigation_reminders:
                    switchFragment(reminderFragment);
                    return true;
                case R.id.navigation_list:
                    switchFragment(listFragment);
                    return true;
                case R.id.navigation_notes:
                    switchFragment(noteFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchFragment(homeFragment);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
        actualFragment = fragment;
    }


}
