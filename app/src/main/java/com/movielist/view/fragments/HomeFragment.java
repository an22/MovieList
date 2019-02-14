package com.movielist.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movielist.R;
import com.movielist.model.Error;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.model_listeners.ErrorListener;
import com.movielist.view.adapters.HomeAdapter;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private static final String TAG = "HOME_FRAGMENT";

    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_home)
    TextView errorTv;

    private Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mUnbinder = ButterKnife.bind(this, view);

        ErrorListener errorListener = error -> {
            mRecyclerView.setVisibility(View.GONE);
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText(error);
            Log.e(TAG,error);
        };

        if(getArguments() != null) {

            Serializable serializableUser = getArguments().getSerializable(User.USER);
            HomeAdapter adapter;

            User user = (User)serializableUser;

            if(user != null) {

                adapter = new HomeAdapter(
                        getContext(),
                        errorListener,
                        user.getLanguage(),
                        user.getCountry(),
                        (Configuration)getArguments().getSerializable(Configuration.TAG));

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setHasFixedSize(true);

            }
            else {
                errorListener.onError(Error.USER_ERROR);
            }

        }
        else errorListener.onError(Error.BAD_ARGUMENTS);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
