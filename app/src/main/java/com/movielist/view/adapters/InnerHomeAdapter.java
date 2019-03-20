package com.movielist.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movielist.R;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.Result;
import com.movielist.model.entity.moviedetails.Movie;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.activities.MovieActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InnerHomeAdapter extends RecyclerView.Adapter<InnerHomeAdapter.InnerViewHolder> {

    private Result result;
    private Configuration mConfiguration;
    private Context mContext;


    public InnerHomeAdapter(Context context, Result result, UINetworkListener listener) {
        this.result = result;
        result.setListener(new UINetworkListener() {
            @Override
            public void onLoaded() {
                notifyDataSetChanged();
                listener.onLoaded();
            }

            @Override
            public void onStart() {
                listener.onStart();
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
        this.mContext = context;
    }

    public void setConfiguration(Configuration configuration) {
        mConfiguration = configuration;
    }


    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getRootView().getContext()).inflate(R.layout.inner_item,parent,false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return result.getResults().size();
    }


    class InnerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster)
        ImageView poster;

        @BindView(R.id.title)
        TextView title;

        InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(int pos){
            title.setText(result.getResults().get(pos).getTitle());
            if(result.getResults().get(pos).getImagePath() != null) {
                Glide.with(mContext).load(
                        mConfiguration.getImageConfig().getSecureBaseUrl() +
                                mConfiguration.getImageConfig().getPosterSizes()[3] +
                                result.getResults().get(pos).getImagePath())
                        .into(poster);
            }else{
                poster.setImageResource(R.drawable.blur60);
            }

        }

        @OnClick(R.id.poster)
        void onPosterTouch(){
            if(Build.VERSION.SDK_INT < 23) {
                Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.poster_scaling);
                poster.startAnimation(anim);
            }

            Bundle arguments = new Bundle();
            arguments.putSerializable(Configuration.TAG,mConfiguration);
            arguments.putInt(Movie.TAG,result.getResults().get(getLayoutPosition()).getID());
            Intent movieActivity = new Intent(mContext, MovieActivity.class);

            movieActivity.putExtras(arguments);

            mContext.startActivity(movieActivity);
        }
    }
}
