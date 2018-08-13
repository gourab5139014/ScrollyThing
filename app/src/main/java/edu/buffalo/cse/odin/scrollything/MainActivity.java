package edu.buffalo.cse.odin.scrollything;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import edu.buffalo.cse.odin.scrollything.models.RecyclerRow;
import edu.buffalo.cse.odin.scrollything.nodb.NoDBAdapter;
import edu.buffalo.cse.odin.scrollything.sqlite.MyAdapter;
import edu.buffalo.cse.odin.scrollything.sqlite.SqliteDBAdapter;

public class MainActivity extends AppCompatActivity{
    NoDBAdapter noDBAdapter;
    MyAdapter myAdapter;
    ArrayList<RecyclerRow> rows = new ArrayList<>(); // Currently just being used for SQLiteDBAdapter
//    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initUI_nodb();
        initUI_sqlite();
        // Turn on this for RealmDB Operations
//        mRealm = Realm.getDefaultInstance();
//        initUI_realm();
    }

    private void initUI_sqlite(){
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        noDBAdapter = new NoDBAdapter(this, animalNames);
        myAdapter = new MyAdapter(this, rows);
//        recyclerView.setAdapter(noDBAdapter);
        fetchSqlite();
        if(rows.size()>1)
        {
            recyclerView.setAdapter(myAdapter);
        }
    }

    private void fetchSqlite() {
        SqliteDBAdapter db=new SqliteDBAdapter(this);
        db.openDB();
        rows.clear();
        Cursor c=db.getAllRows();
        while (c.moveToNext()) //TODO This needs to be specific for the benchmark. Populate this.rows here
        {
//            int id=c.getInt(0);
            String name=c.getString(1);
//            String pos=c.getString(2);
            //CREATE PLAYER
//            Player p=new Player(name,pos,id);
//            players.add(p);
        }
    }



    private void initUI_nodb(){
        // data to populate the RecyclerView with
        ArrayList<String> rows = new ArrayList<>();
        rows.add("Horse");
        rows.add("Cow");
        rows.add("Camel");
        rows.add("Sheep");
        rows.add("Goat");
        rows.add("Horse");
        rows.add("Cow");
        rows.add("Camel");
        rows.add("Sheep");
        rows.add("Goat");
        rows.add("Horse");
        rows.add("Cow");
        rows.add("Camel");
        rows.add("Sheep");
        rows.add("Goat");
        rows.add("Horse");
        rows.add("Cow");
        rows.add("Camel");
        rows.add("Sheep");
        rows.add("Goat");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noDBAdapter = new NoDBAdapter(this, rows);
//        noDBAdapter.setClickListener(this);
        recyclerView.setAdapter(noDBAdapter);
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

}
