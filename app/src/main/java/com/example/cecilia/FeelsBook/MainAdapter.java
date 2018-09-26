package com.example.cecilia.FeelsBook;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    public Context context;

    private List<Record> records;

    public MainAdapter(List<Record> records) {

        this.records = records;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, final int position) {

        holder.mood.setText(records.get(position).getMood());
        holder.date.setText(records.get(position).getDate());
        holder.comment.setText(records.get(position).getComment());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Record toView = records.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("mood",toView.getMood());
                bundle.putString("date",toView.getDate());
                bundle.putString("comment",toView.getComment());
                Intent intent = new Intent(v.getContext(), ViewRecordActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "View " + toView.getMood(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mood;
        public TextView date;
        public TextView comment;
        public Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.context = context;
            mood = (TextView) itemView.findViewById(R.id.Mood);
            date = (TextView) itemView.findViewById(R.id.Date);
            comment = (TextView) itemView.findViewById(R.id.Comment);
            itemView.setClickable(true);
        }
    }
}

