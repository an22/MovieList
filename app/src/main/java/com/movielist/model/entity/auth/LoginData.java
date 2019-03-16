package com.movielist.model.entity.auth;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.movielist.database.KeyContract;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.model_interfaces.LoginModel;

public class LoginData implements LoginModel {


    private RequestToken mRequestToken;
    private AccessToken mAccessToken;
    private KeyDbHelper mKeyDbHelper;

    public LoginData(KeyDbHelper keyDbHelper) {
        mKeyDbHelper = keyDbHelper;
    }

    @Override
    public void setRequestToken(RequestToken requestToken) {
        mRequestToken = requestToken;
    }

    @Override
    public void setAccessToken(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    @Override
    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    @Override
    public RequestToken getRequestToken() {

        return mRequestToken;
    }

    @Override
    public boolean saveDataToDb(String column, String data) {
        try(SQLiteDatabase db = mKeyDbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KeyContract.KeyColumns.COLUMN_NAME_TITLE, column);
            values.put(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE, data);
            db.insert(KeyContract.KeyColumns.TABLE_NAME, null, values);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean loadRequestTokenFromDb() {
        try (SQLiteDatabase db = mKeyDbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM "
                     + KeyContract.KeyColumns.TABLE_NAME + " WHERE "
                     + KeyContract.KeyColumns.COLUMN_NAME_TITLE + "="
                     + "\"" + KeyDbHelper.REQUEST_TOKEN + "\"", null)) {
            cursor.moveToNext();
            int id = cursor.getColumnIndexOrThrow(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE);
            String data = cursor.getString(id);
            setRequestToken(new RequestToken(data));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean loadAccessTokenFromDb() {
        try (SQLiteDatabase db = mKeyDbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM "
                     + KeyContract.KeyColumns.TABLE_NAME + " WHERE "
                     + KeyContract.KeyColumns.COLUMN_NAME_TITLE + "="
                     + "\"" + AccessToken.TAG + "\"", null)) {
            cursor.moveToNext();
            int id = cursor.getColumnIndexOrThrow(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE);
            String data = cursor.getString(id);
            setAccessToken(new AccessToken(data));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
