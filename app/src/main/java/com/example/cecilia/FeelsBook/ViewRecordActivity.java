package com.example.cecilia.FeelsBook;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_record);
        TextView User_mood = findViewById(R.id.User_Mood);
        TextView User_date = findViewById(R.id.User_Date);
        TextView User_comment = findViewById(R.id.User_Comment);
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String MOOD = bundle.getString("mood");
        String DATE = bundle.getString("date");
        String COMMENT = bundle.getString("comment");
        setTitle(MOOD+"   "+DATE);
        User_mood.setText(MOOD);
        User_date.setText(DATE);
        User_comment.setText(COMMENT);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.ViewContentColor)));

    }
}
