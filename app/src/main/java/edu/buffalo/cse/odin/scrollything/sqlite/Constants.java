package edu.buffalo.cse.odin.scrollything.sqlite;

/**
 * Created by Hp on 3/17/2016.
 */
public class Constants {
    //COLUMNS
    public static final String ROW_ID="id";
    public static final String NAME = "name";
    public static final String POSITION = "position";

    //DB PROPERTIES
    public static final String DB_NAME="b_DB";
    public static final String TB_NAME="b_TB";
    public static final int DB_VERSION='1';


    //CREATE TABLE STATEMENTS
    public static final String CREATE_TB="CREATE TABLE b_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,position TEXT NOT NULL);";
}
