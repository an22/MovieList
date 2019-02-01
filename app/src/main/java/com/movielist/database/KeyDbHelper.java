package com.movielist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.movielist.database.KeyContract.KeyColumns;

public class KeyDbHelper extends SQLiteOpenHelper {

    public static final String REQUEST_TOKEN = "RequestToken";
    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String GUEST_SESSION = "GuestSession";
    public static final String SESSION = "UserSession";
    public static final String DATABASE_ERROR = "Database error, contact us";

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KeyStore.db";

    public KeyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + KeyColumns.TABLE_NAME + " (" +
                KeyColumns._ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," +
                KeyColumns.COLUMN_NAME_TITLE + " TEXT," +
                KeyColumns.COLUMN_NAME_SUBTITLE + " TEXT)";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE = "DROP TABLE IF EXISTS " + KeyColumns.TABLE_NAME;
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
