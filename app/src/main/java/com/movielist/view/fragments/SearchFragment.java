package com.movielist.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.movielist.R;
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

public class SearchFragment extends Fragment implements Sender {


    private final String TAG = "SEARCH_FRAGMENT";

    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.search_field)
    EditText searchField;

    @BindView(R.id.tabs)
    TabLayout tabsLayout;

    private SearchResultAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_search,container,false);
         ButterKnife.bind(this,view);

         adapter = new SearchResultAdapter(getChildFragmentManager());
         pager.setAdapter(adapter);
         tabsLayout.setupWithViewPager(pager);
         MovieFragment fragment = new MovieFragment();
         if(getArguments() != null) {
             Bundle args = new Bundle();
             args.putSerializable(Configuration.TAG, getArguments().getSerializable(Configuration.TAG));
             fragment.setArguments(args);
         }
         adapter.addFragment(fragment,"Movies");

         return view;
    }

    @OnEditorAction(R.id.search_field)
    boolean onSearch(TextView v, int actionId){
        if(actionId == EditorInfo.IME_ACTION_SEARCH && v.getText().length() != 0 && getActivity()!= null){
            searchField.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
            sendMessage(String.valueOf(v.getText()));
            return true;
        }
        return false;
    }


    @Override
    public void sendMessage(String data) {
        int pos = tabsLayout.getSelectedTabPosition();
        adapter.getItem(pos).onRecieve(data);
    }
}
