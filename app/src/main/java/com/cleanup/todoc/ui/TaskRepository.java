package com.cleanup.todoc.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.data.TaskData;
import com.cleanup.todoc.data.TaskDataBase;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final Context context;
    private final List<TaskData> taskData;
    private final TaskDao taskDao;
    private final TaskDataBase dataBase;


    public TaskRepository(Context context){
        this.context = context.getApplicationContext();
        this.dataBase = Room.databaseBuilder(this.context,
                TaskDataBase.class, "task_database").build();
        this.taskDao = dataBase.taskDao();
        this.taskData = taskDao.getAll();
    }
    @NonNull
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (TaskData data: this.taskData
        ) {
            tasks.add(new Task(data.taskId, data.projectId, data.taskName, data.creationTimeStamp));
        }
        return tasks;
    }

    public void updateDataBase(ArrayList<Task> tasks){
        for (Task task: tasks
             ) {
            taskDao.insertAll((new TaskData(task.getId(), task.getName(), task.getProjectId(), task.getCreationTimestamp())));
        }
    }

    public void deleteTaskOnDataBase(Task task){
        taskDao.delete(new TaskData(task.getId(),task.getName(), task.getProjectId(), task.getCreationTimestamp()));
    }
}
