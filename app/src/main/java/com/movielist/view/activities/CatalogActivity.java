package com.movielist.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movielist.R;
import com.movielist.database.KeyDbHelper;
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

    private CatalogPresenter mPresenter;
    private static final String USER_ERROR = "Cannot find user,try to restart";
    public static final String LANGUAGE = "language";
    public static final String COUNTRY = "country";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;


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
            mPresenter.loadUser(session);
        }
        else {
            onError(USER_ERROR);
        }

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() != bottomNavigation.getSelectedItemId()){
                switch (item.getItemId()){
                    case R.id.run_home:{
                        item.setIcon(R.drawable.ic_home_white_24dp);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_outline_view_list_24dp);
                        bottomNavigation.getMenu().getItem(3).setIcon(R.drawable.ic_person_outline_white_24dp);
                        runHome(mPresenter.getConfiguration());
                        return true;
                    }
                    case R.id.run_catalog:{
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        item.setIcon(R.drawable.ic_view_list_white_24dp);
                        bottomNavigation.getMenu().getItem(3).setIcon(R.drawable.ic_person_outline_white_24dp);
                        runCatalog(mPresenter.getConfiguration());
                        return true;
                    }
                    case R.id.run_search:{
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_outline_view_list_24dp);
                        bottomNavigation.getMenu().getItem(3).setIcon(R.drawable.ic_person_outline_white_24dp);
                        runSearch(mPresenter.getConfiguration());
                        return true;
                    }
                    case R.id.run_profile:{
                        item.setIcon(R.drawable.ic_person_white_24dp);
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_outline_view_list_24dp);
                        runProfile(mPresenter.getConfiguration());
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
    public void runHome(Configuration configuration) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(User.USER, mPresenter.getUser());
        args.putSerializable(Configuration.TAG,configuration);
        fragment.setArguments(args);
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
        args.putSerializable(User.USER, mPresenter.getUser());
        args.putSerializable(Configuration.TAG,configuration);
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void runCatalog(Configuration configuration) {
    }

    @Override
    public void runProfile(Configuration configuration) {
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
