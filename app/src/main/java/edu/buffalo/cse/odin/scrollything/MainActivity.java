package edu.buffalo.cse.odin.scrollything;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.buffalo.cse.odin.scrollything.models.RecyclerRow;
import edu.buffalo.cse.odin.scrollything.nodb.NoDBAdapter;
import edu.buffalo.cse.odin.scrollything.sqlite.CreateDB;
import edu.buffalo.cse.odin.scrollything.sqlite.MyAdapter;
import edu.buffalo.cse.odin.scrollything.sqlite.Queries;
import edu.buffalo.cse.odin.scrollything.sqlite.SqliteDBAdapter;
import edu.buffalo.cse.odin.scrollything.sqlite.utils.PocketBenchUtils;

public class MainActivity extends AppCompatActivity{
    static String PDE = "PocketData";
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
        Log.e(PocketBenchUtils.TAG,"Rows returned -- "+rows.size());
        if(rows.size()>1)
        {
            recyclerView.setAdapter(myAdapter);
        }
    }

    private void fetchSqlite() {
        final int[] workload_array = {
                R.raw.workload_a, R.raw.workload_b,
                R.raw.workload_c, R.raw.workload_d,
                R.raw.workload_e, R.raw.workload_f,
                R.raw.workload_ia, R.raw.workload_ib,
                R.raw.workload_ic,
        };

        // Map workload handle to ASCII input:
        String[] workloads = {"A", "B", "C", "D", "E", "F", "IA", "IB", "IC"};
        HashMap<String, Integer> load_map = new HashMap<String, Integer>();
        for (int i = 0; i < workloads.length; i++) {
            load_map.put(workloads[i], workload_array[i]);
        }

        PocketBenchUtils.database = "SQL"; // ["SQL", "WAL", "BDB", "BDB100"]
        PocketBenchUtils.workload = "A"; // ["A", "B", "C", "D", "E", "F", "IB", "IC"]
        PocketBenchUtils.governor = "ondemand"; // ["performance", "interactive", "conservative", "ondemand", "userspace", "powersave"]
        PocketBenchUtils.delay = "lognormal"; // ["0ms", "1ms", "lognormal"]

        Log.d(PDE, "Parameter Database:  " + PocketBenchUtils.database);
        Log.d(PDE, "Parameter Workload:  " + PocketBenchUtils.workload);
//	Log.d(PDE, "Parameter WL handle:  " + load_map.get(Utils.workload));  // sanity check on hashmap
        Log.d(PDE, "Parameter Governor:  " + PocketBenchUtils.governor);

        Log.d(PDE, "Parameter Delay:  " + PocketBenchUtils.delay);
        long start = System.currentTimeMillis();
        int tester;
        if(!PocketBenchUtils.doesDBExist(this,PocketBenchUtils.SQLiteDBName)){
            Log.d(PDE, "Creating DB");
            //Create the databases from the JSON
            CreateDB createDB = new CreateDB(this);
            tester = createDB.create(load_map.get(PocketBenchUtils.workload));
            if(tester != 0){
                this.finishAffinity();
            }
            this.finishAffinity();
        }
        else {
            Log.d(PDE, "DB exists -- Running DB Benchmark");

            String singleJsonString = PocketBenchUtils.jsonToString(this, load_map.get(PocketBenchUtils.workload));
            PocketBenchUtils.jsonStringToObject(singleJsonString);


            //Run the queries specified in the JSON on the newly created databases
            Queries queries = new Queries(this);
            rows = queries.startQueries();
//            if (tester != 0){
//                Log.d(PocketBenchUtils.TAG, "Trying to kill MainActivity");
//                this.finishAffinity();
//            }
//            Log.d(PocketBenchUtils.TAG, "NOT killing MainActivity");

            //Find what queries were not executed successfully in the SQL or BDB traces
//            PocketBenchUtils.findMissingQueries(this);
//            //Calculate total time of the traces
//            long end = System.currentTimeMillis();
//
//            long delta = end - start;
//            double elapsedSeconds = delta / 1000.0;
//
//            File file = new File(this.getFilesDir().getPath() + "/time");
//            FileOutputStream fos;
//            try {
//                fos = this.openFileOutput(file.getName(), Context.MODE_APPEND);
//                fos.write((elapsedSeconds + "\n").getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        // My Code
//        SqliteDBAdapter db=new SqliteDBAdapter(this);
//        db.openDB();
//        rows.clear();
//        Cursor c=db.getAllRows();
//        while (c.moveToNext()) //TODO This needs to be specific for the benchmark. Populate this.rows here
//        {
////            int id=c.getInt(0);
//            String name=c.getString(1);
////            String pos=c.getString(2);
//            //CREATE PLAYER
////            Player p=new Player(name,pos,id);
////            players.add(p);
//        }
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
