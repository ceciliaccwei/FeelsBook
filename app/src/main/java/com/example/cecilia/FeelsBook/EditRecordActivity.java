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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditRecordActivity extends AppCompatActivity {
    Spinner mood;
    EditText date;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String MOOD = bundle.getString("mood");
        String DATE = bundle.getString("date");
        String COMMENT = bundle.getString("comment");
        Spinner mood = (Spinner)findViewById(R.id.mood_hint);
        List<String> MOODLIST =  new ArrayList<String>();
        MOODLIST.add(MOOD);
        String[] MOODLIST_all = getResources().getStringArray(R.array.MoodList);
        for (int i=1;i < MOODLIST_all.length;i++){
            String MoodItem = MOODLIST_all[i];
            if (!MOODLIST.contains(MoodItem)){
                MOODLIST.add(MoodItem);
            }

        }
        ArrayAdapter<String> spinAdp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,MOODLIST);
        spinAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood.setAdapter(spinAdp);

        int Year_origin = Integer.valueOf(DATE.substring(0,4));
        int Month_origin = Integer.valueOf(DATE.substring(5,7))-1;
        int Day_origin = Integer.valueOf(DATE.substring(8,10));
        int Hour_origin = Integer.valueOf(DATE.substring(11,13));
        int Min_origin = Integer.valueOf(DATE.substring(14,16));

        DatePicker Date_origin = findViewById(R.id.date_hint);

        Date_origin.updateDate(Year_origin,Month_origin,Day_origin);

        TimePicker time = findViewById(R.id.time_hint);
        time.setIs24HourView(true);
        time.setCurrentHour(Hour_origin);
        time.setCurrentMinute(Min_origin);

        TextView USERCONTEXT = (TextView) findViewById(R.id.comment_hint);
        USERCONTEXT.setText(COMMENT);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.ViewContentColor)));


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

        Spinner mood = (Spinner)findViewById(R.id.mood_hint);
        DatePicker date = findViewById(R.id.date_hint);

        TimePicker time = findViewById(R.id.time_hint);
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
        Bundle bundle = this.getIntent().getExtras();
        int recordID = bundle.getInt("id");
        //noinspection SimplifiableIfStatement
        if (id == R.id.recordSaveButton) {
            Record record = db.recordDAO().findById(recordID);
            record.setMood(recordMood);
            record.setDate(recordDate);
            record.setComment(recordComment);
            db.recordDAO().updateAll(record);
            Toast.makeText(this, "Record Edited", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditRecordActivity.this,MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
