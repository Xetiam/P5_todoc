package com.cleanup.todoc.utils;

import androidx.annotation.NonNull;

import com.cleanup.todoc.data.TaskData;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;

public class DataUtils {

    @NonNull
    public static ArrayList<TaskData> getDefaultTaskData(Long insertedDate) {
        ArrayList<TaskData> taskData = new ArrayList<>();
        taskData.add(new TaskData(1L, "1", 1L, insertedDate));
        taskData.add(new TaskData(2L, "2", 1L, insertedDate));
        return taskData;
    }

    @NonNull
    public static ArrayList<Task> getTaskToSortByName(Long insertedDate) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, 1L,"G",  insertedDate));
        tasks.add(new Task(2L,  1L,"D", insertedDate));
        tasks.add(new Task(1L,  1L,"A", insertedDate));
        tasks.add(new Task(1L,  1L,"1", insertedDate));
        tasks.add(new Task(2L, 1L,"2",  insertedDate));
        tasks.add(new Task(2L,  1L,"F", insertedDate));
        return tasks;
    }

    @NonNull
    public static ArrayList<Task> getTaskToSortByDate(Long insertedDate, Long insertedDate2, Long insertedDate3) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, 1L,"G",  insertedDate3));
        tasks.add(new Task(2L,  1L,"D", insertedDate));
        tasks.add(new Task(1L,  1L,"A", insertedDate2));
        tasks.add(new Task(1L,  1L,"1", insertedDate));
        tasks.add(new Task(2L, 1L,"2",  insertedDate3));
        tasks.add(new Task(2L,  1L,"F", insertedDate2));
        return tasks;
    }

    @NonNull
    public static ArrayList<Task> getDefaultTask(Long insertedDate) {
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(new Task(1, 1, "1", insertedDate));
        expectedResult.add(new Task(2, 1, "2", insertedDate));
        return expectedResult;
    }
}
