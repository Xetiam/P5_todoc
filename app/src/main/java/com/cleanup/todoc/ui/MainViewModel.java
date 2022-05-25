package com.cleanup.todoc.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;

public class MainViewModel extends ViewModel {
    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;
    private ArrayList<Task> tasks = new  ArrayList();
    private final MutableLiveData<MainState> _state = new MutableLiveData<>();
    final LiveData<MainState> state = _state;

    public void initTasks(ArrayList<Task> mTasks) {
        this.tasks = mTasks;
        getSortMethod(R.id.filter_recent_first);
    }

    public void getSortMethod(int id) {
        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }
        updateTasks();
    }
    /**
     * Updates the list of tasks in the UI
     */
    public void updateTasks() {
        if (tasks.size() == 0) {
            this._state.postValue(new MainStateWithNoTasks());
        } else {

            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;

            }
            this._state.postValue(new MainStateWithTasks(tasks));
        }
    }

    public void addNewTask(Task task) {
        tasks.add(task);
        updateTasks();
    }

    public void removeTasks(Task task) {
        tasks.remove(task);
        updateTasks();
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
