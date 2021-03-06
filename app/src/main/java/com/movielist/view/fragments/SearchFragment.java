package com.movielist.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.movielist.R;
import com.movielist.model.Error;
import com.movielist.model.ResultTypes;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.Result;
import com.movielist.presenter.model_listeners.Sender;
import com.movielist.view.adapters.SearchResultAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnTouch;
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


        if (getArguments() != null ) {
            for(ResultTypes type:ResultTypes.values()){
                Bundle args = new Bundle();
                args.putSerializable(Configuration.TAG,getArguments().getSerializable(Configuration.TAG));
                args.putSerializable(Result.TYPE,type);
                ResultFragment fragment = new ResultFragment();
                fragment.setArguments(args);
                adapter.addFragment(fragment,type.getName());
            }

            pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position){
                    if(searchField.getText().length() != 0){
                        sendMessage(String.valueOf(searchField.getText()));
                    }
                }
            });

            pager.setOffscreenPageLimit(adapter.getCount());

        }else{
            Log.e(TAG, Error.BAD_ARGUMENTS);
        }
        return view;
    }

    @OnEditorAction(R.id.search_field)
    boolean onSearch(TextView v, int actionId){
        if(actionId == EditorInfo.IME_ACTION_SEARCH && v.getText().length() != 0 && getActivity()!= null){
            searchField.clearFocus();
            InputMethodManager in =
                    (InputMethodManager)getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            //Because in may produce NullPointerException
            if (in != null) {
                in.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
            }
            sendMessage(String.valueOf(v.getText()));
            return true;
        }
        return false;
    }

    @OnTouch(R.id.search_field)
    boolean onTouchSearch(MotionEvent event){
        if(event.getRawX() >= searchField.getWidth() -
                searchField.getCompoundDrawables()[2].getBounds().width() -
                searchField.getPaddingEnd()&& event.getAction() == MotionEvent.ACTION_UP){
            searchField.getText().clear();
        }
        return false;
    }


    @Override
    public void sendMessage(String data) {
        int pos = tabsLayout.getSelectedTabPosition();
        Fragment childFragment = (Fragment) adapter.instantiateItem(pager,pos);
        ((ReceiverFragment) childFragment).onReceive(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
