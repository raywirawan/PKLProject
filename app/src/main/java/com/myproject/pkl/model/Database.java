package com.myproject.pkl.model;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = IdentifiedObject.class, exportSchema = false, version = 1)
public abstract class Database extends RoomDatabase {
    private static final String DB_NAME = "local_db";
    private static Database instance;

    public static synchronized Database getInstance(Context context){
       if (instance == null){
           instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                   .fallbackToDestructiveMigration().build();
       }
       return instance;
    }

    public abstract IdentifiedObjectDao identifiedObjectDao();
}
