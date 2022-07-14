package com.cleanup.todoc.ui;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.utils.DialogDismissCallBack;

import java.util.ArrayList;

public class MainState {
}
class MainStateWithTasks extends MainState {
    private ArrayList<Task> tasks;
    public MainStateWithTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
class MainStateWithNoTasks extends MainState {
}
class MainStateOnCreate extends MainState {
    private Boolean isOnError;
    private DialogDismissCallBack callBack;
    private ArrayList<Task> tasks;


    MainStateOnCreate(Boolean isOnError, DialogDismissCallBack callBack, ArrayList<Task> tasks){
        this.isOnError = isOnError;
        this.callBack = callBack;
        this.tasks = tasks;
    }

    public Boolean getOnError() {
        return isOnError;
    }

    public DialogDismissCallBack getCallBack() {
        return callBack;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
