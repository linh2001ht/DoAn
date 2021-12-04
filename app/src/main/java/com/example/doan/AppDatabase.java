package com.example.doan;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ChuSoHuu.class,BienSoXe.class,LichSuVaoRa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    public static AppDatabase instance;
    public static AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,AppDatabase.class,"quanlyBSX").allowMainThreadQueries().build();
        }
        return instance;
    }
}
