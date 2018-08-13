package edu.buffalo.cse.odin.scrollything.sqlite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.buffalo.cse.odin.scrollything.R;


public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView areaText;

    public MyViewHolder(View itemView){
        super(itemView);
        areaText = (TextView)itemView.findViewById(R.id.area);
    }

    //NOTE Implement onClick later if required
}
