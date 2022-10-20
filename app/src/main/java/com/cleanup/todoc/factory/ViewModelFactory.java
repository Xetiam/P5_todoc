package com.cleanup.todoc.factory;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.data.AppDatabase;
import com.cleanup.todoc.ui.MainViewModel;
import com.cleanup.todoc.ui.utils.MainRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory viewModelFactory;

    private final MainRepository mainRepository;

    private final Executor ioExecutor = Executors.newFixedThreadPool(4);


    private ViewModelFactory(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        mainRepository = new MainRepository(
                appDatabase.getTaskDao(),
                appDatabase.getProjectDao());
    }

    public static ViewModelFactory getInstance(Application application) {
        if (viewModelFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (viewModelFactory == null) {
                    viewModelFactory = new ViewModelFactory(application.getApplicationContext());
                }
            }
        }
        return viewModelFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(mainRepository, ioExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public <T extends ViewModel> T obtainViewModel(Class<T> modelClass) {
        return viewModelFactory.create(modelClass);

    }

}