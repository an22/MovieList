package com.movielist.view.adapters;

import com.movielist.view.view_interfaces.ReceiverFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SearchResultAdapter extends FragmentPagerAdapter {

    private List<String> tabs;
    private List<ReceiverFragment> mFragmentList;

    public SearchResultAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
        tabs = new ArrayList<>();
    }

    public void addFragment(ReceiverFragment fragment, String title){
        mFragmentList.add(fragment);
        tabs.add(title);
        notifyDataSetChanged();

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @NonNull
    @Override
    public ReceiverFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
