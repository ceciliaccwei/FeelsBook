package com.example.cecilia.FeelsBook;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
// DAO
@Dao
public interface RecordDAO {

    @Query("SELECT * FROM record order by record.Date desc , record.id desc ")
    List<Record> getAllRecords();

    @Insert
    void insertAll(Record... records);

    @Update
    void updateAll(Record... records);

    @Query ("SELECT * FROM record WHERE record.id = :recordID")
    Record findById(int recordID);

    @Delete
    void deleteAll(Record record);

    @Query ("SELECT * FROM record WHERE record.Mood = :recordM")
    List<Record> fetchbyMood(String recordM);

}
