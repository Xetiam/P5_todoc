package com.cleanup.todoc.ui;

import com.cleanup.todoc.model.Task;

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
