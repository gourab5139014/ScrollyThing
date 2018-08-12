package edu.buffalo.cse.odin.scrollything.sqlite;

/**
 * Created by Hp on 3/17/2016.
 */
public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME = "name";
    static final String POSITION = "position";

    //DB PROPERTIES
    static final String DB_NAME="b_DB";
    static final String TB_NAME="b_TB";
    static final int DB_VERSION='1';


    //CREATE TABLE STATEMENTS
    static final String CREATE_TB="CREATE TABLE b_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,position TEXT NOT NULL);";
}
