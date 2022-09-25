package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.utils.DialogDismissCallBack;
import com.cleanup.todoc.ui.utils.MainRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {
    @NonNull
    private final MainRepository mRepository;

    @NonNull
    private final Executor mIoExecutor;
    private final MediatorLiveData<MainState> _state = new MediatorLiveData<>();
    final LiveData<MainState> state = _state;
    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;
    private ArrayList<Task> mTasks = new ArrayList<>();
    private Project[] mProjects;

    public MainViewModel(MainRepository repository, Executor ioExecutor) {
        mRepository = repository;
        mIoExecutor = ioExecutor;
    }

    public void onLoadViewProject() {
        _state.addSource(mRepository.getProject(), projects -> {
            if(projects.size() != 0){
                mProjects = projects.toArray(new Project[0]);
            }
            else{
                mProjects = new Project[]{};
            }
            if(mProjects.length == 0){
                mIoExecutor.execute(mRepository::addDefaultProject);
            }
            updateProjects();
        });
    }
    public void onLoadView() {
        _state.addSource(mRepository.getTasks(), tasks -> {
            mTasks = tasks;
            updateTasks();
        });
    }

    private void updateProjects() {
        if (mProjects.length == 0) {
            this._state.postValue(new MainStateWithNoProjects());
        } else {
            this._state.postValue(new MainStateWithProjects(mProjects));
        }
    }

    public void setSortMethod(int id) {
        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }
        sort();
    }

    /**
     * Updates the list of tasks in the UI
     */
    public void updateTasks() {
        if (mTasks.size() == 0) {
            this._state.postValue(new MainStateWithNoTasks());
        } else {
            this._state.postValue(new MainStateWithTasks(mTasks));
        }
    }

    public void sort() {
        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(mTasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(mTasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(mTasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(mTasks, new Task.TaskOldComparator());
                break;
        }
        updateTasks();
    }

    public void addNewTask(Task task) {
        mTasks.add(task);
        mIoExecutor.execute(() -> mRepository.addTaskToDataBase(task));
        updateTasks();
    }

    public void removeTasks(Task task) {
        mTasks.remove(task);
        mIoExecutor.execute(() -> mRepository.deleteTaskOnDataBase(task));
        updateTasks();
    }

    public void createTask(String taskName, Object selectedItem, DialogDismissCallBack callBack) {
        Project taskProject = null;
        if (selectedItem instanceof Project) {
            taskProject = (Project) selectedItem;
        }
        // If a name has not been set
        if (taskName.trim().isEmpty()) {
            this._state.postValue(new MainStateOnCreate(true, callBack, null));
        }
        // If both project and name of the task have been set
        else if (taskProject != null) {
            Task task = new Task(
                    taskProject.getId(),
                    taskName,
                    new Date().getTime()
            );
            addNewTask(task);
            this._state.postValue(new MainStateOnCreate(false, callBack, mTasks));
        }
    }

    public void getProjects() {
        mIoExecutor.execute(() -> mRepository.getProject());
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
