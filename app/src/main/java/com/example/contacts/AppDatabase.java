package com.example.contacts;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ContactDAO contactDAO();

    public AppDatabase()
    {

    }
    public static AppDatabase instance = null;
    public static AppDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context, AppDatabase.class, "contacts").build();
        }
        return instance;
    }
}
