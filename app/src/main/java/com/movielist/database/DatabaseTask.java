package com.movielist.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class DatabaseTask extends AsyncTask<Void,Void,Boolean> {

    SQLiteDatabase database;

    public DatabaseTask(SQLiteDatabase database){
        this.database = database;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        return true;
    }
}
