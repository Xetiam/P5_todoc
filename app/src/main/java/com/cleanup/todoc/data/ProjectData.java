package com.cleanup.todoc.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ProjectData {
    @PrimaryKey(autoGenerate = true)
    public Long projectId;
    @ColumnInfo(name = "project_name")
    public String name;
    @ColumnInfo(name = "project_color")
    public int color;

    public ProjectData(Long projectId, String name, int color) {
        this.projectId = projectId;
        this.name = name;
        this.color = color;
    }

    @Ignore
    public ProjectData(String name, int color) {
        this.name = name;
        this.color = color;
    }
}
