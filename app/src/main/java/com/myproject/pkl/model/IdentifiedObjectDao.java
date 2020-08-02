package com.myproject.pkl.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IdentifiedObjectDao {
    @Query("Select * from identified_object")
    List<IdentifiedObject> getIdentifiedObjectList();
    @Query("Delete from identified_object")
    void deleteAll();
    @Insert
    void insertIdentifiedObject(IdentifiedObject io);
    @Update
    void updateIdentifiedObject(IdentifiedObject io);
    @Delete
    void deleteIdentifiedObject(IdentifiedObject io);

}
