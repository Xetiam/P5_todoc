package com.cleanup.todoc.ui.utils;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.cleanup.todoc.data.ProjectDao;
import com.cleanup.todoc.data.ProjectData;
import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.data.TaskData;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;

public class MainRepository {
    private final TaskDao taskDao;
    private final ProjectDao projectDao;

    public MainRepository(TaskDao taskDao, ProjectDao projectDao) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
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

    @MainThread
    public LiveData<ArrayList<Project>> getProject(){
        return Transformations.map(projectDao.getAll(), input -> {
            ArrayList<Project> projects = new ArrayList<>();
            for (ProjectData data : input) {
                projects.add(new Project(data.projectId, data.name, data.color));
            }
            return projects;
        });
    }

    @WorkerThread
    public void addTaskToDataBase(Task task){
        taskDao.insert((new TaskData(task.getName(), task.getProjectId(), task.getCreationTimestamp())));
    }

    @WorkerThread
    public void deleteTaskOnDataBase(Task task) {
        taskDao.delete(new TaskData(task.getId(), task.getName(), task.getProjectId(), task.getCreationTimestamp()));
    }

    @WorkerThread
    public void addDefaultProject() {
        ProjectData[] defaultProjects = new ProjectData[]{
            new ProjectData("Projet Tartampion", 0xFFEADAD1),
                    new ProjectData("Projet Lucidia", 0xFFB4CDBA),
                    new ProjectData("Projet Circus", 0xFFA3CED2),
        };
        projectDao.insertAll(defaultProjects);
    }
}
