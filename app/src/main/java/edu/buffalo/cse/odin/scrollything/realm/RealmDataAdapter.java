package edu.buffalo.cse.odin.scrollything.realm;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.buffalo.cse.odin.scrollything.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDataAdapter extends RecyclerView.Adapter<RealmDataAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    private Realm mRealm;
    private RealmResults<Data> mResults;

    public RealmDataAdapter(Context context, Realm realm, RealmResults<Data> results) {
        mRealm = realm;
        mInflater = LayoutInflater.from(context);
        setResults(results);
    }

    public Data getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = mResults.get(position);
        holder.setData(data.getData());
    }

    public void setResults(RealmResults<Data> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return mResults.get(position).getTimestamp();
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void add(String text) {

        //Create a new object that contains the data we want to add
        Data data = new Data();
        data.setData(text);

        //Set the timestamp of creation of this object as the current time
        data.setTimestamp(System.currentTimeMillis());

        //Start a transaction
        mRealm.beginTransaction();

        //Copy or update the object if it already exists, update is possible only if your table has a primary key
        mRealm.copyToRealmOrUpdate(data);

        //Commit the transaction
        mRealm.commitTransaction();

        //Tell the Adapter to update what it shows.
        notifyDataSetChanged();
    }

    public void remove(int position) {

        //Start a transaction
        mRealm.beginTransaction();

        //Remove the item from the desired position
        mResults.remove(position);

        //Commit the transaction
        mRealm.commitTransaction();

        //Tell the Adapter to update what it shows
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView area;

        public ViewHolder(View itemView) {
            super(itemView);
            area = (TextView) itemView.findViewById(R.id.area);
        }

        public void setData(String text) {
            area.setText(text);
        }
    }
}
