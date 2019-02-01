package com.movielist.view.activities;

import android.os.Bundle;

import com.movielist.database.KeyDbHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class CatalogActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            System.out.println(savedInstanceState.getString(KeyDbHelper.GUEST_SESSION));
        }
    }
}
