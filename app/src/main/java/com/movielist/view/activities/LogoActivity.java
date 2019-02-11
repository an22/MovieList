package com.movielist.view.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.movielist.R;
import com.movielist.database.KeyContract.KeyColumns;
import com.movielist.database.KeyDbHelper;

import androidx.appcompat.app.AppCompatActivity;


public class LogoActivity extends AppCompatActivity {

    private final String TAG = "LOGO_ACTIVITY";

    //Database helper
    private KeyDbHelper keyHelper;

    private String data;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        Handler mHandler = new Handler();  // Handler allows us to do our task after some time

        keyHelper = new KeyDbHelper(this); // Database creation if not exist, or binding if exists

        //New thread declaration which will start new activity
        //Lambda expression
        Runnable nextActivity = () -> {

            // If the user is logged in then we run Catalog Activity,
            // otherwise we send him to LoginActivity

            if(checkAuthorization()) {
                Intent intent = new Intent(LogoActivity.this, CatalogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(type,data);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        //Set delay for starting new activity
        mHandler.postDelayed(nextActivity,1500);
    }


    private boolean checkAuthorization(){

        try(SQLiteDatabase db = keyHelper.getReadableDatabase(); //Try with resources allows not to worry about cleaning resources

            //SQL query that return count of all elements in table
            Cursor curs = db.rawQuery("SELECT count(*) FROM " + KeyColumns.TABLE_NAME,null))

        {
            curs.moveToFirst();
            int count = curs.getInt(0);

            if(count > 0) {//if database isn`t empty

                Cursor cursor = db.rawQuery("SELECT * FROM "
                        + KeyColumns.TABLE_NAME + " WHERE "
                        + KeyColumns.COLUMN_NAME_TITLE + "="
                        + "\"" + KeyDbHelper.SESSION + "\"" + "OR "
                        + KeyColumns.COLUMN_NAME_TITLE + "="
                        + "\"" + KeyDbHelper.GUEST_SESSION + "\"", null);

                cursor.moveToNext();

                int id = cursor.getColumnIndexOrThrow(KeyColumns.COLUMN_NAME_SUBTITLE);
                int typeId = cursor.getColumnIndexOrThrow(KeyColumns.COLUMN_NAME_TITLE);

                data = cursor.getString(id);
                type = cursor.getString(typeId);

                cursor.close();
            }
            else {
                Log.d(TAG,"Empty");
                return false;
            }
        }catch (Exception e){
            //Database error
            Log.e(TAG,e.getMessage());
            return false;
        }
        return true;
    }

}
