package com.movielist.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movielist.R;
import com.movielist.model.Error;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.DownloadTypes;
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.presenter.model_listeners.ErrorListener;
import com.movielist.presenter.model_listeners.Reciever;
import com.movielist.view.activities.CatalogActivity;
import com.movielist.view.adapters.InnerHomeAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements Reciever {

    private final String TAG = "MOVIE_FRAGMENT";

    private MovieResult mResult;

    private final int spanCount = 2;

    private ErrorListener listener;

    @BindView(R.id.error_result)
    TextView errorLayout;

    @BindView(R.id.rv_result)
    RecyclerView mRecyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result,container,false);
        ButterKnife.bind(this,view);
        listener = error -> {
            mRecyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            errorLayout.setText(error);
            Log.e(TAG,error);
        };

        if(getActivity() != null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String language = preferences.getString(CatalogActivity.LANGUAGE,"en");
            String country = preferences.getString(CatalogActivity.COUNTRY,"US");
            if(getArguments() != null) {
                mResult = new MovieResult(language, country);
                mResult.setType(DownloadTypes.QUERY);
                InnerHomeAdapter adapter = new InnerHomeAdapter(getContext(), mResult, listener);
                adapter.setConfiguration((Configuration)getArguments().getSerializable(Configuration.TAG));
                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
                mRecyclerView.setAdapter(adapter);
            }else listener.onError(Error.BAD_ARGUMENTS);
        }
        else {
            listener.onError(Error.ACTIVITY_NOT_FOUND);
        }
        return view;
    }


    @Override
    public void onRecieve(String data) {
        mResult.loadFromQuery(data);
    }
}
