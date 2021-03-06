package com.movielist.database;

import android.provider.BaseColumns;

public final class KeyContract {

    //Each of inner classes represent a database Table

    public static final class KeyColumns implements BaseColumns{
        public static final String TABLE_NAME = "KeyTable";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "token";
    }

    private KeyContract(){}
}
