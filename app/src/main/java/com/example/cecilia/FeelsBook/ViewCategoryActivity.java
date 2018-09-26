package com.example.cecilia.FeelsBook;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"FeelsBook")
                .allowMainThreadQueries().build();
        String cate = getIntent().getStringExtra("category");
        final List<Record> records;
        if (cate.equals("All")){
            records = db.recordDAO().getAllRecords();
        }
        else{
            records = db.recordDAO().fetchbyMood(cate);
        }

        setTitle(cate + " (" + String.valueOf(records.size()) + ")");
        mRecyclerView = (RecyclerView) findViewById(R.id.list_records);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(records);
        mRecyclerView.setAdapter(mAdapter);
         /*
        Original code Copyright (c) 2017 Wenxi Zeng[1]
        Modified code Copyright (c) 2018 Cecilia Wei
        Licensed under the CC-BY-SA 3.0[2]
        Original code posted to this question[3] and answer[4] from
        stackoverflow.com where user contributions are licensed under
        CC-BY-SA 3.0 with attribution required.

        [1]: https://stackoverflow.com/users/8296631/wenxi-zeng
        [2]: https://creativecommons.org/licenses/by-sa/3.0/
        [3]: https://stackoverflow.com/q/44965278
        [4]: https://stackoverflow.com/a/45062745
        */
        /*Changes
        got color string from color resource file
        completed onClick functions to edit and delete
         */

        /* **************************************************************************************************** */
        SwipeHelper swipeHelper = new SwipeHelper(this, mRecyclerView) {
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton("Delete", getResources().getColor(R.color.DeleteButtonColor),
                        new UnderlayButtonClickListener() {

                            public void onClick(int position) {
                                Record record = records.get(position);
                                Toast.makeText(ViewCategoryActivity.this, "Record deleted", Toast.LENGTH_SHORT).show();
                                db.recordDAO().deleteAll(record);
                                records.remove(record);
                                mAdapter.notifyItemRemoved(position);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        }
                ));

                underlayButtons.add(new UnderlayButton("Edit", getResources().getColor(R.color.EditButtonColor),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Record record = records.get(pos);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id",record.getId());
                                bundle.putString("mood",record.getMood());
                                bundle.putString("date",record.getDate());
                                bundle.putString("comment",record.getComment());
                                Intent intent = new Intent(ViewCategoryActivity.this,EditRecordActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }
                        }
                ));
            }
        };
        /* **************************************************************************************************** */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}