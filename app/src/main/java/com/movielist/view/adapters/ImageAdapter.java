package com.movielist.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.movielist.R;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.moviedetails.MovieImages;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private MovieImages mImages;
    private Configuration mConfiguration;

    public ImageAdapter(Context context, MovieImages images, Configuration configuration){
        mContext = context;
        mImages = images;
        mConfiguration = configuration;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_view,parent,false);
        return new ImageViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mImages.getBackdrops() == null) return 0;
        if(mImages.getBackdrops().length != 0) return mImages.getBackdrops().length;
        return mImages.getPosters().length;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster)
        ImageView mImageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(int position){
            if(mImages.getBackdrops().length != 0) {
                Glide.with(mContext)
                        .load(mConfiguration.getImageConfig().getSecureBaseUrl() +
                                mConfiguration.getImageConfig().getBackdropSizes()[3] +
                                mImages.getBackdrops()[position].getPosterPath())
                        .into(mImageView);
            }else {
                Glide.with(mContext)
                        .load(mConfiguration.getImageConfig().getSecureBaseUrl() +
                                mConfiguration.getImageConfig().getPosterSizes()[3] +
                                mImages.getPosters()[position].getPosterPath())
                        .into(mImageView);
            }
        }
    }
}
