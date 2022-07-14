package com.cleanup.todoc.ui.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Room;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.data.TaskData;
import com.cleanup.todoc.data.TaskDataBase;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final TaskDataBase dataBase;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);


    public TaskRepository(Context context) {
        this.dataBase = Room.databaseBuilder(context.getApplicationContext(),
                TaskDataBase.class, "task_database").build();
        this.taskDao = dataBase.taskDao();
    }

    @NonNull
    public LiveData<ArrayList<Task>> getTasks() {
        return Transformations.map(taskDao.getAll(), input -> {
            ArrayList<Task> tasks = new ArrayList<>();
            for (TaskData data : input) {
                tasks.add(new Task(data.taskId, data.projectId, data.taskName, data.creationTimeStamp));
            }
            return tasks;
        });
    }

    public void updateDataBase(ArrayList<Task> tasks) {
        for (Task task : tasks
        ) {
            executorService.execute(() -> taskDao.insertAll((new TaskData(task.getName(), task.getProjectId(), task.getCreationTimestamp()))));
        }
    }

    public void addTaskToDataBase(Task task){
        executorService.execute(() -> taskDao.insertAll((new TaskData(task.getName(), task.getProjectId(), task.getCreationTimestamp()))));
    }

    public void deleteTaskOnDataBase(Task task) {
        executorService.execute(() -> taskDao.delete(new TaskData(task.getId(), task.getName(), task.getProjectId(), task.getCreationTimestamp())));
    }
}
