package com.movielist.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movielist.R;
import com.movielist.model.entity.catalog.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private final String[] types = {"Upcoming","Top rated","Popular"};

    private Context mContext;
    private User mUser;


    public HomeAdapter(Context context, User user) {
        mContext = context;
        mUser = user;
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
        return 3;
    }

    class HomeHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.inner_rv)
        RecyclerView mRecyclerView;

        @BindView(R.id.header)
        TextView header;

        private InnerHomeAdapter adapter;

        HomeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


            LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(manager);
            adapter = new InnerHomeAdapter(mUser,mContext);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);
        }

        void bind(int pos){
            header.setText(types[pos]);
            adapter.setPosition(pos);
        }

    }
}
