import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class MyRecyclerVIewAdapter extends RecyclerView.Adapter {
    /*
    Refer https://stackoverflow.com/questions/26517855/using-the-recyclerview-with-a-database
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
