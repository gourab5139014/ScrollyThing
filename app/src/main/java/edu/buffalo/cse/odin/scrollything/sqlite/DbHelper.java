package edu.buffalo.cse.odin.scrollything.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hp on 3/17/2016.
 */
//Refer https://github.com/Oclemy/RecyclerSQLiteExample
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    //WHEN TB IS CREATED
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_TB);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    //UPGRADE TB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TB_NAME);
        onCreate(db);
    }
}
