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
import com.movielist.model.ResultTypes;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.Result;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.LoadMoreListener;
import com.movielist.view.activities.CatalogActivity;
import com.movielist.view.adapters.InnerHomeAdapter;
import com.movielist.view.view_interfaces.ReceiverFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ResultFragment extends ReceiverFragment {

    private static final String TAG = "MOVIE_FRAGMENT";

    private Result mResult;

    private int count;

    private String currentText;

    private static final int spanCount = 2;

    @BindView(R.id.error_result)
    TextView errorLayout;

    @BindView(R.id.rv_result)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    private Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result,container,false);
        mUnbinder = ButterKnife.bind(this,view);

        UINetworkListener listener = new UINetworkListener() {
            @Override
            public void onLoaded() {
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStart() {
                count++;

                if(count <= 1) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                if(errorLayout.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                    errorLayout.setText(error);
                }
                Log.e(TAG, error);
            }
        };

        if(getActivity() != null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String language = preferences.getString(CatalogActivity.LANGUAGE,"en");
            String country = preferences.getString(CatalogActivity.COUNTRY,"US");
            if(getArguments() != null) {
                if(savedInstanceState != null && savedInstanceState.getSerializable(Result.TAG) != null){
                    mResult = (Result)savedInstanceState.getSerializable(Result.TAG);
                }else {
                    mResult = Result.createResult((ResultTypes) getArguments().getSerializable(Result.TYPE), language, country);
                }
                InnerHomeAdapter adapter = new InnerHomeAdapter(getContext(), mResult, listener,getArguments().getString(KeyDbHelper.SESSION));
                adapter.setConfiguration((Configuration)getArguments().getSerializable(Configuration.TAG));
                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setOnScrollListener(new LoadMoreListener(mResult,6));
                mRecyclerView.setHasFixedSize(true);

            }else listener.onError(Error.BAD_ARGUMENTS);
        }
        else {
            listener.onError(Error.ACTIVITY_NOT_FOUND);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Result.TAG,mResult);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onReceive(String data) {
        if(!data.equals(currentText)) {
            count = 0;
            currentText = data;
            mResult.loadFromQuery(data);
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
