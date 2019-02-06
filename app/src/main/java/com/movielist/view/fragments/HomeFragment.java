package com.movielist.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movielist.R;
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.model.entity.catalog.User;
import com.movielist.view.adapters.HomeAdapter;
import com.movielist.view.view_interfaces.HomeView;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mUnbinder = ButterKnife.bind(this, view);

        if(getArguments() != null) {

            Serializable user = getArguments().getSerializable(User.USER);



            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(manager);

            HomeAdapter adapter = new HomeAdapter(getContext(), (User) user);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);


        }

        return view;
    }

    @Override
    public void setUpRecyclerView(ArrayList<MovieResult> results){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
