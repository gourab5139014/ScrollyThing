package edu.buffalo.cse.odin.scrollything;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.buffalo.cse.odin.scrollything.nodb.MyRecyclerViewAdapter;
import edu.buffalo.cse.odin.scrollything.realm.Data;
import edu.buffalo.cse.odin.scrollything.realm.RealmDataAdapter;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity{
    MyRecyclerViewAdapter adapter;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI_nodb();
        // Turn on this for RealmDB Operations
//        mRealm = Realm.getDefaultInstance();
//        initUI_realm();
    }

    private void initUI_realm() {
//        //Asynchronous query
//        RealmResults<Data> mResults = mRealm.where(Data.class).findAllAsync("data");
//
//        //Tell me when the results are loaded so that I can tell my Adapter to update what it shows
//        mResults.addChangeListener(new RealmChangeListener<Data>() {
//            @Override
//            public void onChange() {
//                mAdapter.notifyDataSetChanged();
//                Toast.makeText(ActivityMain.this, "onChange triggered", Toast.LENGTH_SHORT).show();
//            }
//        });
//        mRecycler = (RecyclerView) findViewById(R.id.recycler);
//        mRecycler.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new DataAdapter(this, mRealm, mResults);
//
//        //Set the Adapter to use timestamp as the item id for each row from our database
//        mAdapter.setHasStableIds(true);
//        mRecycler.setAdapter(mAdapter);
    }

    private void initUI_nodb(){
        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
//        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
