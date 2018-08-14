package edu.buffalo.cse.odin.scrollything.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.util.Log;

import edu.buffalo.cse.odin.scrollything.models.RecyclerRow;
import edu.buffalo.cse.odin.scrollything.sqlite.utils.PocketBenchUtils;

public class Queries {
    JSONObject workloadJsonObject;
    Context context;
//    Utils utils;

    public Queries(Context inContext){
//        utils = new Utils();
        workloadJsonObject = PocketBenchUtils.workloadJsonObject;
        context = inContext;
    }

    public ArrayList<RecyclerRow> startQueries(){
//        int tester;
//        String PDE = "PocketData";
        Log.d(PocketBenchUtils.TAG, "Start startQueries()");
        ArrayList<RecyclerRow> dataRows = null;
//        PocketBenchUtils.putMarker("{\"EVENT\":\"Parameters\", \"Database\":\"" + PocketBenchUtils.database + "\", \"Workload\":\"" + PocketBenchUtils.workload + "\", \"Governor\":\"" + PocketBenchUtils.governor + "\", \"Delay\":\"" + PocketBenchUtils.delay + "\"}");
//        PocketBenchUtils.putMarker("START: App started\n");
//        PocketBenchUtils.putMarker("{\"EVENT\":\"" + PocketBenchUtils.database + "_START\"}");

        if (PocketBenchUtils.database.equals("SQL")) {
            Log.d(PocketBenchUtils.TAG, "Testing SQL");
            dataRows = sqlQueries();
        }

//        if ((PocketBenchUtils.database.equals("SQL")) || (PocketBenchUtils.database.equals("WAL"))) {
//            Log.d(PDE, "Testing SQL");
//            tester = sqlQueries();
//        } else if ((PocketBenchUtils.database.equals("BDB")) || (PocketBenchUtils.database.equals("BDB100"))) {
//            Log.d(PDE, "Testing BDB");
//            tester = bdbQueries();
//        } else {
//            Log.d(PDE, "Error -- Unknown Database Requested");
//            return 1;
//        }
//        if (tester != 0) {
//            Log.d(PDE, "Error -- Bad Benchmark Result");
//            return 1;
//        }

        Log.d(PocketBenchUtils.TAG, "End startQueries()");
//        PocketBenchUtils.putMarker("{\"EVENT\":\"" + PocketBenchUtils.database + "_END\"}");
//        PocketBenchUtils.putMarker("END: app finished\n");

        return dataRows;

    }

    private ArrayList<RecyclerRow> sqlQueries(){
        ArrayList<RecyclerRow> dataRows=new ArrayList<>();
        SQLiteDatabase db = context.openOrCreateDatabase("SQLBenchmark",0,null);
        int sqlException = 0;

        try {
            JSONArray benchmarkArray = workloadJsonObject.getJSONArray("benchmark");
            for(int i = 0; i < benchmarkArray.length(); i ++){
                JSONObject operationJson = benchmarkArray.getJSONObject(i);
                Object operationObject = operationJson.get("op");
                String operation = operationObject.toString();

                switch (operation) {
                    case "query": {
                        sqlException = 0;
                        Object queryObject = operationJson.get("sql");
                        String query = queryObject.toString();

                        try {

                            if(query.contains("SELECT")){
                                Cursor cursor = db.rawQuery(query,null);
//                                Log.d(PocketBenchUtils.TAG, "Running "+query);
                                if(cursor.moveToFirst()) {
                                    int numColumns = cursor.getColumnCount();
                                    StringBuilder sb = new StringBuilder();
                                    for(int r=0;r<cursor.getCount();r++){ //Iterate over each row in the cursor
                                        for(int c=0;c<numColumns;c++){
                                            sb.append(cursor.getString(c));
                                            sb.append("|");
                                        }
                                        //NOTE If CRLF is required, add here
                                        cursor.moveToNext();
                                    }
//                                    Log.d(PocketBenchUtils.TAG,sb.toString());
                                    dataRows.add(new RecyclerRow(sb.toString()));
////                                    Log.d(PocketBenchUtils.TAG, "Returned columns "+numColumns);
//                                    do {
//                                        Log.d(PocketBenchUtils.TAG, cursor.getString(cursor.getColumnIndex("DAT_STR_1")));
//                                        int j=0;
//                                        while (j< numColumns) {
//                                            j++;
//                                        }
//                                    } while(cursor.moveToNext());
                                }
                                cursor.close();

                            }
                            else {
                                db.execSQL(query);
                            }

                        }
                        catch (SQLiteException e){
                            sqlException = 1;
                            Log.e(PocketBenchUtils.TAG, e.getLocalizedMessage());
                            continue;
                        }
                        break;
                    }
                    case "break": {

                        if(sqlException == 0) {
                            Object breakObject = operationJson.get("delta");
                            int breakTime = Integer.parseInt(breakObject.toString());
                            int tester = PocketBenchUtils.sleepThread(breakTime);
//                            if(tester != 0){
//                                return 1;
//                            }

                        }
                        sqlException = 0;
                        break;
                    }
                    default:
                        db.close();
//                        return 1;
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
            db.close();
//            return 1;
        }
        finally {
            db.close();
            return dataRows;
        }
//        db.close();
//        return 0;
    }

    private int bdbQueries(){

        Connection con = PocketBenchUtils.jdbcConnection("BDBBenchmark");
        Statement stmt;
        int sqlException = 0;

        try {
            JSONArray benchmarkArray = workloadJsonObject.getJSONArray("benchmark");
            for(int i = 0; i < benchmarkArray.length(); i ++){
                JSONObject operationJson = benchmarkArray.getJSONObject(i);
                Object operationObject = operationJson.get("op");
                String operation = operationObject.toString();
                switch (operation) {
                    case "query": {
                        sqlException = 0;
                        Object queryObject = operationJson.get("sql");
                        String query = queryObject.toString();

                        try {

                            stmt = con.createStatement();

                            if(query.contains("UPDATE")){
                                int tester = stmt.executeUpdate(query);
                                if(tester == 0 || tester < 0){
                                    stmt.close();
                                }
                            }
                            else {
                                Boolean test = stmt.execute(query);
                                if (!test){
                                    stmt.close();
                                }
                                stmt.close();


                            }

                        }
                        catch (SQLiteException e){
                            sqlException = 1;

                            continue;
                        } catch (SQLException e) {
                            sqlException = 1;

                            e.printStackTrace();
                            continue;
                        }
                        break;
                    }
                    case "break": {

                        if(sqlException == 0) {
                            Object breakObject = operationJson.get("delta");
                            int breakTime = Integer.parseInt(breakObject.toString());
                            int tester = PocketBenchUtils.sleepThread(breakTime);
                            if(tester != 0){
                                return 1;
                            }

                        }
                        sqlException = 0;
                        break;
                    }
                    default:
                        con.close();
                        return 1;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
}
