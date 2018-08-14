package edu.buffalo.cse.odin.scrollything.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import edu.buffalo.cse.odin.scrollything.sqlite.utils.PocketBenchUtils;

public class CreateDB {
    Context context;

    public CreateDB(Context contextIn){
        context = contextIn;
    }

    public int create(int workload){
        Log.d(PocketBenchUtils.TAG, "inside CreateDB.create()");
//        Utils utils = new Utils();
        String singleJsonString = PocketBenchUtils.jsonToString(context, workload);
        JSONObject jsonObject = PocketBenchUtils.jsonStringToObject(singleJsonString);
        Log.d(PocketBenchUtils.TAG, "inside CreateDB.create() : Created JSON object");
        int tester = populateSqlDb(jsonObject);
        if(tester != 0){
            return 1;
        }

//        Connection con = PocketBenchUtils.jdbcConnection("BDBBenchmark");
//        tester = populateBdb(jsonObject, con);
//        if(tester != 0){
//            return 1;
//        }

        return 0;
    }

    private int populateSqlDb(JSONObject jsonObject){

        SQLiteDatabase db = context.openOrCreateDatabase("SQLBenchmark",0,null);
        Log.d(PocketBenchUtils.TAG, "Inside CreateDB.populateSqlDb : Created DB SQLBenchmark");
        try {
            JSONArray initArray = jsonObject.getJSONArray("init");
            for (int i = 0; i < initArray.length(); i++){
                JSONObject obj2 = initArray.getJSONObject(i);
                Object sqlObject = obj2.get("sql");
                String sqlStatement = sqlObject.toString();
                Log.d(PocketBenchUtils.TAG, "Executing "+sqlStatement);
                db.execSQL(sqlStatement);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(PocketBenchUtils.TAG, e.getLocalizedMessage());
            return 1;  //NOTE Change these magic numbers to Enums with informative names
        } catch(Exception e1){
            e1.printStackTrace();
            Log.e(PocketBenchUtils.TAG, e1.getLocalizedMessage());
            return 1;
        }
        Log.d(PocketBenchUtils.TAG, "Finished CreateDB.populateSqlDb");
        return 0;
    }

    private int populateBdb(JSONObject jsonObject, Connection con){

        Statement stmt;

        try {
            JSONArray initArray = jsonObject.getJSONArray("init");
            for (int i = 0; i < initArray.length(); i++){
                stmt = con.createStatement();

                JSONObject obj2 = initArray.getJSONObject(i);
                Object sqlObject = obj2.get("sql");
                String sqlStatement = sqlObject.toString();
                stmt.execute(sqlStatement);
                stmt.close();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
