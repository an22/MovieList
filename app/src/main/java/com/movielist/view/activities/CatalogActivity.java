package com.movielist.view.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.movielist.R;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.entity.catalog.CatalogData;
import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.activity_presenters.CatalogPresenter;
import com.movielist.view.fragments.HomeFragment;
import com.movielist.view.view_interfaces.CatalogView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CatalogActivity extends AppCompatActivity implements CatalogView {

    private CatalogPresenter mPresenter;

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
            runHome();
        }
        else {
            onError("12");
        }

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() != bottomNavigation.getSelectedItemId()){
                switch (item.getItemId()){
                    case R.id.run_home:{
                        item.setIcon(R.drawable.ic_home_white_24dp);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_outline_view_list_24dp);
                        bottomNavigation.getMenu().getItem(3).setIcon(R.drawable.ic_person_outline_white_24dp);
                        runHome();
                        return true;
                    }
                    case R.id.run_catalog:{
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        item.setIcon(R.drawable.ic_view_list_white_24dp);
                        bottomNavigation.getMenu().getItem(3).setIcon(R.drawable.ic_person_outline_white_24dp);
                        runCatalog();
                        return true;
                    }
                    case R.id.run_search:{
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_outline_view_list_24dp);
                        bottomNavigation.getMenu().getItem(3).setIcon(R.drawable.ic_person_outline_white_24dp);
                        runSearch();
                        return true;
                    }
                    case R.id.run_profile:{
                        item.setIcon(R.drawable.ic_person_white_24dp);
                        bottomNavigation.getMenu().getItem(0).setIcon(R.drawable.ic_outline_home_24dp);
                        bottomNavigation.getMenu().getItem(1).setIcon(R.drawable.ic_outline_view_list_24dp);
                        runProfile();
                        return true;
                    }
                    default: return false;
                }
            }
            else return false;
        });

    }

    public void runHome() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(User.USER,mPresenter.getUser());
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }

    public void runSearch() {
    }

    public void runCatalog() {
    }

    public void runProfile() {
    }

    @Override
    public void onError(String message) {

    }
}
