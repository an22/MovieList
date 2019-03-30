package com.movielist.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movielist.R;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.DownloadTypes;
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.LoadMoreListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private final DownloadTypes[] mTypes = DownloadTypes.values();

    private final String[] keys = {"Upcoming","Top rated","Popular"};

    private Context mContext;

    private Configuration mConfiguration;

    private String language;
    private String country;
    private String session;

    private int userID;

    private UINetworkListener mListener;


    public HomeAdapter(Context context,
                       UINetworkListener errorListener,
                       String language, String country,
                       Configuration configuration,
                       String session,int userID) {
        mContext = context;
        mListener = errorListener;
        this.session = session;
        this.language = language;
        this.country = country;
        this.userID = userID;
        this.mConfiguration = configuration;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home,parent,false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return keys.length;
    }

    class HomeHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.inner_rv)
        RecyclerView mRecyclerView;

        @BindView(R.id.header)
        TextView header;

        private MovieResult result;

        private InnerHomeAdapter adapter;


        HomeHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);

            result = new MovieResult(language,country);
            adapter = new InnerHomeAdapter(mContext,result,mListener,session,userID);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setOnScrollListener(new LoadMoreListener(result,3));
        }

        void bind(int pos){
            header.setText(keys[pos]);
            result.setType(mTypes[pos]);
            result.loadMore();
            adapter.setConfiguration(mConfiguration);
        }


    }
}
