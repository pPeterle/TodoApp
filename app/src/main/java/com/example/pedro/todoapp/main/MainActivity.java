package com.example.pedro.todoapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.completedTasks.TaskCompletedActivity;
import com.example.pedro.todoapp.tasks.TasksFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;

public class MainActivity extends AppCompatActivity {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private Drawer mDrawer;
    private Toolbar mToolbar;

    private TasksFragment tasksFragment = TasksFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.main_menu);

        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_completedTasks:
                startActivity(new Intent(this, TaskCompletedActivity.class));
                return true;
        }
        return false;
    }

    public void setupDrawer() {

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(new PrimaryDrawerItem().withIdentifier(1).withName("Projects"))
                .addDrawerItems(new SecondaryDrawerItem().withIdentifier(2).withName("Trabalho"))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int id = (int) drawerItem.getIdentifier();

                        switch (id) {
                            case 2:
                                switchFragment(tasksFragment);
                                break;
                        }

                        return false;
                    }
                })
                .build();
    }

}
