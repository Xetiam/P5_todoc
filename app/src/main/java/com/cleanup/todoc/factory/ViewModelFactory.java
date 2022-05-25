

package com.cleanup.todoc.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.ui.MainViewModel;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactory viewModelFactory;


    private ViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static ViewModelFactory getInstance() {
        if (viewModelFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (viewModelFactory == null) {
                    viewModelFactory = new ViewModelFactory();
                }
            }
        }
        return viewModelFactory;
    }

    public <T extends ViewModel> T obtainViewModel(Class<T> modelClass) {
        return viewModelFactory.create(modelClass);

    }

}