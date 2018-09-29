package com.example.pedro.todoapp.di;

import android.app.Application;

import com.example.pedro.todoapp.App;
import com.example.pedro.todoapp.di.module.AppModule;
import com.example.pedro.todoapp.di.module.FragmentModule;
import com.example.pedro.todoapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {FragmentModule.class, ActivityBuilder.class, AppModule.class, ViewModelModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(App App);
}