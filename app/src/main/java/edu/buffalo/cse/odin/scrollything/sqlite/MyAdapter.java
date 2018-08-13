package edu.buffalo.cse.odin.scrollything.sqlite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.buffalo.cse.odin.scrollything.R;
import edu.buffalo.cse.odin.scrollything.models.RecyclerRow;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<RecyclerRow> rows;

    public MyAdapter(Context context, ArrayList<RecyclerRow> rows) {
        this.context = context;
        this.rows = rows;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, null);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.areaText.setText(rows.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
