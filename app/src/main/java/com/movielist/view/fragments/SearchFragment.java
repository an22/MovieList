package com.movielist.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.movielist.R;
import com.movielist.model.Error;
import com.movielist.model.entity.Configuration;
import com.movielist.presenter.model_listeners.Sender;
import com.movielist.view.adapters.SearchResultAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements Sender {


    private static final String TAG = "SEARCH_FRAGMENT";

    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.search_field)
    EditText searchField;

    @BindView(R.id.tabs)
    TabLayout tabsLayout;

    private SearchResultAdapter adapter;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,container,false);


        mUnbinder = ButterKnife.bind(this,view);

        adapter = new SearchResultAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabsLayout.setupWithViewPager(pager);

        MovieFragment movieFragment = new MovieFragment();
        PersonFragment personFragment = new PersonFragment();
        TvFragment tvFragment = new TvFragment();

        if(getArguments() != null) {
             Bundle args = new Bundle();
             args.putSerializable(Configuration.TAG, getArguments().getSerializable(Configuration.TAG));
             movieFragment.setArguments(args);
             personFragment.setArguments(args);
             tvFragment.setArguments(args);
        }
        else {
             Log.e(TAG, Error.BAD_ARGUMENTS);
        }
        adapter.addFragment(movieFragment,"Movies");
        adapter.addFragment(personFragment,"People");
        adapter.addFragment(tvFragment,"TV");

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
             @Override
             public void onPageSelected(int position){
                 if(searchField.getText().length() != 0){
                     adapter.getItem(position).onReceive(String.valueOf(searchField.getText()));
                 }
             }
        });

        pager.setOffscreenPageLimit(adapter.getCount());
        return view;
    }

    @OnEditorAction(R.id.search_field)
    boolean onSearch(TextView v, int actionId){
        if(actionId == EditorInfo.IME_ACTION_SEARCH && v.getText().length() != 0 && getActivity()!= null){
            searchField.clearFocus();
            InputMethodManager in =
                    (InputMethodManager)getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            in.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
            sendMessage(String.valueOf(v.getText()));
            return true;
        }
        return false;
    }


    @Override
    public void sendMessage(String data) {
        int pos = tabsLayout.getSelectedTabPosition();
        adapter.getItem(pos).onReceive(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
