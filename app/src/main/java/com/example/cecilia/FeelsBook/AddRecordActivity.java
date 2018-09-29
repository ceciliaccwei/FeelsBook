package com.example.cecilia.FeelsBook;

import android.annotation.TargetApi;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

// Add a new record to the database, including mood, date-time and optional comment.
public class AddRecordActivity extends AppCompatActivity {
    Spinner mood;
    DatePicker date;
    TimePicker time;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record);
        TimePicker time = findViewById(R.id.time_hint);
        time.setIs24HourView(true);
        Spinner mood = (Spinner)findViewById(R.id.mood_hint);
        String[] MOODLIST = getResources().getStringArray(R.array.MoodList);

        ArrayAdapter<String> spinAdp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,MOODLIST);
        spinAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood.setAdapter(spinAdp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.ViewContentColor)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.record_menu, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.N)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        mood = (Spinner)findViewById(R.id.mood_hint);
        date = findViewById(R.id.date_hint);

        time = findViewById(R.id.time_hint);
        time.setIs24HourView(true);

        comment = findViewById(R.id.comment_hint);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"FeelsBook")
                .allowMainThreadQueries().build();
        int id = item.getItemId();
        String recordMood = String.valueOf(mood.getSelectedItem());
        Date selectedDate = new Date(date.getYear()-1900, date.getMonth(), date.getDayOfMonth(),time.getHour(),time.getMinute());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String recordDate = dateFormatter.format(selectedDate);

        String recordComment = comment.getText().toString();
        // if clicked the save button,
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
       if (id == R.id.recordSaveButton) {
           // if the mood if not empty,  save the record to the database
           if (!recordMood.equals("Select your mood \uD83D\uDC47️")) {
               Record record = new Record(recordMood, recordDate, recordComment);
               db.recordDAO().insertAll(record);
               // toast
               Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(AddRecordActivity.this, MainActivity.class));
               return true;
           }
           else{
               Toast.makeText(this,"Please Select Your Mood",Toast.LENGTH_LONG).show();
           }
        }

        return super.onOptionsItemSelected(item);
    }

}
