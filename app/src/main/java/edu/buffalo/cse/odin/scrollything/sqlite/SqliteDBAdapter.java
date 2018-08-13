package edu.buffalo.cse.odin.scrollything.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import edu.buffalo.cse.odin.scrollything.sqlite.utils.DbHelper;

public class SqliteDBAdapter {
    Context c;
    SQLiteDatabase db;
    DbHelper helper;

    public SqliteDBAdapter(Context ctx)
    {
        this.c=ctx;
        helper=new DbHelper(c);
    }

    //OPEN DB
    public SqliteDBAdapter openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return this;
    }

    //CLOSE
    public void close()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    //INSERT DATA TO DB
    public long add(String name,String pos)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME,name);
            cv.put(Constants.POSITION, pos);

            return db.insert(Constants.TB_NAME,Constants.ROW_ID,cv);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    //RETRIEVE ALL ROWS
    public Cursor getAllRows()
    {
        throw new UnsupportedOperationException("Need the implement the benchmark specific queries");
//        String[] columns={Constants.ROW_ID,Constants.NAME,Constants.POSITION};
//
//        return db.query(Constants.TB_NAME,columns,null,null,null,null,null);
    }

    //UPDATE
    public long UPDATE(int id,String name,String pos)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME,name);
            cv.put(Constants.POSITION, pos);

            return db.update(Constants.TB_NAME,cv,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }

    //DELETE
    public long Delete(int id)
    {
        try
        {

            return db.delete(Constants.TB_NAME,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
