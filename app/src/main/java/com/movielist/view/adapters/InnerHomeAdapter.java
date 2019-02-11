package com.movielist.view.adapters;

import android.content.Context;
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
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.presenter.model_listeners.ErrorListener;
import com.movielist.presenter.model_listeners.UINetworkListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InnerHomeAdapter extends RecyclerView.Adapter<InnerHomeAdapter.InnerViewHolder> {

    private MovieResult movies;
    private Configuration mConfiguration;
    private Context mContext;


    public InnerHomeAdapter(Context context, MovieResult movies, ErrorListener errorListener) {
        this.movies = movies;
        movies.setListener(new UINetworkListener() {
            @Override
            public void onLoaded() {
                notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                errorListener.onError(error);
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
        return movies.getShorts().size();
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
            title.setText(movies.getShorts().get(pos).getTitle());
            Glide.with(mContext).load(mConfiguration.getImageConfig().getSecureBaseUrl() + mConfiguration.getImageConfig().getPosterSizes()[3] + movies.getShorts().get(pos).getPosterPath()).into(poster);

        }

        @OnClick(R.id.poster)
        void onPosterTouch(){
                Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.poster_scaling);
                poster.startAnimation(anim);
        }
    }
}
