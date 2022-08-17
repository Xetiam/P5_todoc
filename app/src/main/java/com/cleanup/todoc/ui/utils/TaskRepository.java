package com.cleanup.todoc.ui.utils;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.data.TaskData;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @MainThread
    public LiveData<ArrayList<Task>> getTasks() {
        return Transformations.map(taskDao.getAll(), input -> {
            ArrayList<Task> tasks = new ArrayList<>();
            for (TaskData data : input) {
                tasks.add(new Task(data.taskId, data.projectId, data.taskName, data.creationTimeStamp));
            }
            return tasks;
        });
    }

    @WorkerThread
    public void updateDataBase(ArrayList<Task> tasks) {
        for (Task task : tasks
        ) {
            taskDao.insert((new TaskData(task.getName(), task.getProjectId(), task.getCreationTimestamp())));
        }
    }

    @WorkerThread
    public void addTaskToDataBase(Task task){
        taskDao.insert((new TaskData(task.getName(), task.getProjectId(), task.getCreationTimestamp())));
    }

    @WorkerThread
    public void deleteTaskOnDataBase(Task task) {
        taskDao.delete(new TaskData(task.getId(), task.getName(), task.getProjectId(), task.getCreationTimestamp()));
    }
}
