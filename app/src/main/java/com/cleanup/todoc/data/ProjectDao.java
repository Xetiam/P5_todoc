package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM projectdata")
    LiveData<List<ProjectData>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ProjectData... projects);

}

