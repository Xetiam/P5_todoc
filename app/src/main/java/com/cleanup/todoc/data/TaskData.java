package com.cleanup.todoc.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class TaskData {
    //annotation de génération
    @PrimaryKey(autoGenerate = true)
    public Long taskId;
    @ColumnInfo(name = "task_name")
    public String taskName;
    @ColumnInfo(name = "project_id")
    public Long projectId;
    @ColumnInfo(name = "creation_time_stamp")
    public Long creationTimeStamp;

    public TaskData(Long taskId, String taskName, Long projectId, Long creationTimeStamp) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.projectId = projectId;
        this.creationTimeStamp = creationTimeStamp;
    }

    @Ignore
    public TaskData(String taskName, Long projectId, Long creationTimeStamp) {
        this.taskName = taskName;
        this.projectId = projectId;
        this.creationTimeStamp = creationTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskData taskData = (TaskData) o;
        return taskId.equals(taskData.taskId) && taskName.equals(taskData.taskName) && projectId.equals(taskData.projectId) && creationTimeStamp.equals(taskData.creationTimeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, projectId, creationTimeStamp);
    }
}

