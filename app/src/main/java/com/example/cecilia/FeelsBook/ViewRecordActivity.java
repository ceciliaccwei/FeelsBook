package com.example.cecilia.FeelsBook;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

// view the detail of each category
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}

