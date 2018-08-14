package edu.buffalo.cse.odin.scrollything.sqlite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.buffalo.cse.odin.scrollything.R;
import edu.buffalo.cse.odin.scrollything.models.RecyclerRow;
import edu.buffalo.cse.odin.scrollything.sqlite.utils.PocketBenchUtils;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<RecyclerRow> rows;
    private LayoutInflater _inflater;

    public MyAdapter(Context context, ArrayList<RecyclerRow> rows) {
        this.context = context;
        this.rows = rows;
        this._inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.recycler_row, parent, false);
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, null);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerRow rr = rows.get(position);
        String rowText = rr.getText();
        Log.e(PocketBenchUtils.TAG, "Disp str -- "+rowText);
        holder.areaText.setText(rowText);
//        holder.areaText.setText(rows.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
