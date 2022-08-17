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
    public static ArrayList<Task> getDefaultTask(Long insertedDate) {
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(new Task(1, 1, "1", insertedDate));
        expectedResult.add(new Task(2, 1, "2", insertedDate));
        return expectedResult;
    }
}
