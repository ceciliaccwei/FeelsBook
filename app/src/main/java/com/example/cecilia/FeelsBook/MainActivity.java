package com.example.cecilia.FeelsBook;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // create navigation drawer(total counter)
        // create recyclerView
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"FeelsBook")
                .allowMainThreadQueries().build();

        final List<Record> records = db.recordDAO().getAllRecords();
        mRecyclerView = (RecyclerView) findViewById(R.id.list_records);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(records);
        mRecyclerView.setAdapter(mAdapter);
        SetNavigationText(db,navigationView);

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
        comment added
         */

        /* **************************************************************************************************** */
        SwipeHelper swipeHelper = new SwipeHelper(this, mRecyclerView) {
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton("Delete", getResources().getColor(R.color.DeleteButtonColor),
                        new UnderlayButtonClickListener() {

                            public void onClick(int position) {
                                // if clicked the delete button, delete the current record
                                Record record = records.get(position);
                                Toast.makeText(MainActivity.this, "Record deleted", Toast.LENGTH_SHORT).show();
                                db.recordDAO().deleteAll(record);
                                records.remove(record);
                                mAdapter.notifyItemRemoved(position);
                                SetNavigationText(db, navigationView);
                            }
                        }
                ));

                underlayButtons.add(new UnderlayButton("Edit", getResources().getColor(R.color.EditButtonColor),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // if clicked the edit button, allow user to eit the current record
                                Record record = records.get(pos);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id",record.getId());
                                bundle.putString("mood",record.getMood());
                                bundle.putString("date",record.getDate());
                                bundle.putString("comment",record.getComment());
                                Intent intent = new Intent(MainActivity.this,EditRecordActivity.class);
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
    // drawer action
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // click the item in navigation drawer to view each category.
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"FeelsBook")
                .allowMainThreadQueries().build();

        if (id == R.id.sort_All) {
            Intent intent = new Intent(this, ViewCategoryActivity.class);
            intent.putExtra("category","All");
            startActivity(intent);
        } else if (id == R.id.sort_love) {
            Intent intent = new Intent(this, ViewCategoryActivity.class);
            intent.putExtra("category","❤️ love");
            startActivity(intent);

        }else if (id == R.id.sort_joy) {
                Intent intent = new Intent(this, ViewCategoryActivity.class);
                intent.putExtra("category","\uD83D\uDE0A joy");
                startActivity(intent);
        }
        else if (id == R.id.sort_anger) {
            Intent intent = new Intent(this, ViewCategoryActivity.class);
            intent.putExtra("category","\uD83D\uDE20 anger");
            startActivity(intent);
        }
        else if (id == R.id.sort_surprise) {
            Intent intent = new Intent(this, ViewCategoryActivity.class);
            intent.putExtra("category","\uD83D\uDE3A surprise");
            startActivity(intent);
        }
        else if (id == R.id.sort_sadness) {
            Intent intent = new Intent(this, ViewCategoryActivity.class);
            intent.putExtra("category","\uD83D\uDE12 sadness");
            startActivity(intent);

        }else if (id == R.id.sort_fear) {
            Intent intent = new Intent(this, ViewCategoryActivity.class);
            intent.putExtra("category","\uD83D\uDE28 fear");
            startActivity(intent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void SetNavigationText(AppDatabase db,NavigationView navigationView){
        // count the num of each emotion
        TextView countAll = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_All));
        countAll.setGravity(Gravity.CENTER_VERTICAL);
        countAll.setText(String.valueOf(db.recordDAO().getAllRecords().size()));

        TextView countLove = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_love));
        countLove.setGravity(Gravity.CENTER_VERTICAL);
        countLove.setText(String.valueOf(db.recordDAO().fetchbyMood("❤️ love").size()));

        TextView countJoy = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_joy));
        countJoy.setGravity(Gravity.CENTER_VERTICAL);
        countJoy.setText(String.valueOf(db.recordDAO().fetchbyMood("\uD83D\uDE0A joy").size()));

        TextView countSurprise = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_surprise));
        countSurprise.setGravity(Gravity.CENTER_VERTICAL);
        countSurprise.setText(String.valueOf(db.recordDAO().fetchbyMood("\uD83D\uDE3A surprise").size()));

        TextView countAnger = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_anger));
        countAnger.setGravity(Gravity.CENTER_VERTICAL);
        countAnger.setText(String.valueOf(db.recordDAO().fetchbyMood("\uD83D\uDE20 anger").size()));

        TextView countSadness = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_sadness));
        countSadness.setGravity(Gravity.CENTER_VERTICAL);
        countSadness.setText(String.valueOf(db.recordDAO().fetchbyMood("\uD83D\uDE12 sadness").size()));

        TextView countFear = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.sort_fear));
        countFear.setGravity(Gravity.CENTER_VERTICAL);
        countFear.setText(String.valueOf(db.recordDAO().fetchbyMood("\uD83D\uDE28 fear").size()));
    }

}
