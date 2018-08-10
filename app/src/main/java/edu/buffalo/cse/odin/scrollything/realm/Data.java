package edu.buffalo.cse.odin.scrollything.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Data extends RealmObject {
    @PrimaryKey
    private String data;
    private long timestamp; //The time when this item was added to the database

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
