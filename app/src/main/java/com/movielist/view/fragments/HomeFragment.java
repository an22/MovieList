package com.movielist.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.movielist.R;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.Error;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.activities.CatalogActivity;
import com.movielist.view.adapters.HomeAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    public static final String TAG = "HOME_FRAGMENT";

    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_home)
    TextView errorTv;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    private Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mUnbinder = ButterKnife.bind(this, view);

        UINetworkListener listener = new UINetworkListener() {
            @Override
            public void onLoaded() {
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {
                mRecyclerView.setVisibility(View.GONE);
                errorTv.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                errorTv.setText(error);
                Log.e(TAG,error);
            }
        };

        if(getArguments() != null && getActivity()!= null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            HomeAdapter adapter = new HomeAdapter(
                    getContext(),
                    listener,
                    preferences.getString(CatalogActivity.LANGUAGE,"en"),
                    preferences.getString(CatalogActivity.COUNTRY,"US"),
                    (Configuration)getArguments().getSerializable(Configuration.TAG),
                    getArguments().getString(KeyDbHelper.SESSION),
                    getArguments().getInt(User.USER));

            LinearLayoutManager manager = new LinearLayoutManager(getContext());

            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);

        }
        else listener.onError(Error.BAD_ARGUMENTS);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
