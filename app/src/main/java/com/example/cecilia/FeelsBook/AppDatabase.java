package com.example.cecilia.FeelsBook;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Record.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecordDAO recordDAO();



}
