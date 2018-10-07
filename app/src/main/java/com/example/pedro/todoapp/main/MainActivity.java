package com.example.pedro.todoapp.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.ViewModelFactory;
import com.example.pedro.todoapp.completed.CompletedActivity;
import com.example.pedro.todoapp.data.entity.Table;
import com.example.pedro.todoapp.tasks.TasksFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;

public class MainActivity extends AppCompatActivity {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    ViewModelFactory factory;

    private Drawer mDrawer;
    private Toolbar mToolbar;

    private MainViewModel mViewModel;
    private int tableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.main_menu);

        setupViewModel();

        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        getLifecycle().addObserver(mViewModel);
        mViewModel.getState().observe(this, state -> handleSuccess(state.getData()));
    }

    private void handleSuccess(List<Table> data) {
        List<IDrawerItem> list = new ArrayList<>();
        for (Table table : data) {
            list.add(new SecondaryDrawerItem()
                    .withIdentifier(table.getId())
                    .withName(table.getName()));
        }

        mDrawer.removeAllItems();
        mDrawer.addItem(new ExpandableDrawerItem().withName("Projects").withSubItems(list));
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
                //TODO tornar dinamico o id
                startActivity(CompletedActivity.newInstace(this, tableId));
                return true;
        }
        return false;
    }

    public void setupDrawer() {

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int id = (int) drawerItem.getIdentifier();
                        if (id > 0) {
                            tableId = id;
                            switchFragment(TasksFragment.newInstance(id));
                        }

                        return false;
                    }
                })
                .build();
    }

}
