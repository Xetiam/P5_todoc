package com.cleanup.todoc.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TaskData {
    public TaskData(Long taskId, String taskName, Long projectId, Long creationTimeStamp){
        this.taskId = taskId;
        this.taskName = taskName;
        this.projectId = projectId;
        this.creationTimeStamp = creationTimeStamp;
    }
    @Ignore
    public TaskData(String taskName, Long projectId, Long creationTimeStamp){
        this.taskName = taskName;
        this.projectId = projectId;
        this.creationTimeStamp = creationTimeStamp;
    }
    @PrimaryKey(autoGenerate = true)
    //anotation de génération
    public Long taskId;

    @ColumnInfo(name = "task_name")
    public String taskName;

    @ColumnInfo(name = "project_id")
    public Long projectId;

    @ColumnInfo(name = "creation_time_stamp")
    public Long creationTimeStamp;
}

