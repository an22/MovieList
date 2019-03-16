package com.movielist.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movielist.R;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.Error;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.CatalogData;
import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.activity_presenters.CatalogPresenter;
import com.movielist.view.fragments.HomeFragment;
import com.movielist.view.fragments.SearchFragment;
import com.movielist.view.view_interfaces.CatalogView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CatalogActivity extends AppCompatActivity implements CatalogView {

    private static final String TAG = "CATALOG_ACTIVITY";

    private CatalogPresenter mPresenter;
    public static final String LANGUAGE = "language";
    public static final String COUNTRY = "country";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.catalog_activity_error)
    TextView errorLayout;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);


        Bundle extra = getIntent().getExtras();
        if(extra != null){
            String session = extra.getString(KeyDbHelper.SESSION);
            if(session == null){
                session = extra.getString(KeyDbHelper.GUEST_SESSION);
            }
            CatalogData data = new CatalogData();
            mPresenter = new CatalogPresenter(this,data);
            mPresenter.loadUser(session,this);
        }
        else {
            onError(Error.USER_ERROR);
        }

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() != bottomNavigation.getSelectedItemId()){
                switch (item.getItemId()){
                    case R.id.run_home:{
                        item.setIcon(R.drawable.ic_home_white_24dp);
                        runHome(mPresenter.getConfiguration());
                        hideProgress();
                        return true;
                    }
                    case R.id.run_search:{
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        runSearch(mPresenter.getConfiguration());
                        hideProgress();
                        return true;
                    }
                    default: return false;
                }
            }
            else return false;
        });

    }


    @Override
    public void saveLanguage(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(LANGUAGE,((User) mPresenter.getUser()).getLanguage());
        editor.putString(COUNTRY,((User) mPresenter.getUser()).getCountry());
        editor.apply();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void runHome(Configuration configuration) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(Configuration.TAG,configuration);
        fragment.setArguments(args);
        errorLayout.setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void runSearch(Configuration configuration) {

        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable(Configuration.TAG,configuration);
        fragment.setArguments(args);
        errorLayout.setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onError(String message) {
        errorLayout.setVisibility(View.VISIBLE);
        errorLayout.setText(message);
        Log.e(TAG,message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
