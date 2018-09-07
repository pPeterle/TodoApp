package com.example.pedro.todoapp.view.di;

import android.app.Application;

import com.example.pedro.todoapp.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {FragmentModule.class, ActivityBuilder.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(App App);
}