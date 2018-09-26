package com.example.cecilia.FeelsBook;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Record {
    public Record(String mood, String date, String comment) {
        this.mood = mood;
        this.date = date;
        this.comment = comment;
    }
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Mood")
    private String mood;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}


