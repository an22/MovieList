package com.movielist.view.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.movielist.R;
import com.movielist.database.KeyContract.KeyColumns;
import com.movielist.database.KeyDbHelper;

import androidx.appcompat.app.AppCompatActivity;


public class LogoActivity extends AppCompatActivity {

    private KeyDbHelper keyHelper;
    private String data;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Handler mHandler = new Handler();
        keyHelper = new KeyDbHelper(this);

        //New thread declaration which will start new activity
        Runnable nextActivity = () -> {
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

        try(SQLiteDatabase db = keyHelper.getReadableDatabase();
            Cursor curs = db.rawQuery("SELECT count(*) FROM " + KeyColumns.TABLE_NAME,null))
        {
            curs.moveToFirst();
            int count = curs.getInt(0);
            if(count > 0) {
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
                System.out.println("YEAH");
            }
            else {
                System.out.println("EMPTY");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
