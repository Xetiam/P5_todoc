package com.cleanup.todoc.ui;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.utils.DialogDismissCallBack;

import java.util.ArrayList;

public class MainState {
}
class MainStateWithNoProjects extends MainState{
}
class MainStateWithProjects extends MainState{
    private final Project[] projects;
    public MainStateWithProjects(Project[] projects){
        this.projects = projects;
    }

    public Project[] getProjects() {
        return projects;
    }
}
class MainStateWithTasks extends MainState {
    private final ArrayList<Task> tasks;
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
    private final Boolean isOnError;
    private final DialogDismissCallBack callBack;
    private final ArrayList<Task> tasks;


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
