package com.movielist.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class FullScreenImageAdapter extends PagerAdapter {

    private String[] imageURL;
    private Context mContext;

    public FullScreenImageAdapter(String[] imageURL, Context context){
        this.imageURL = imageURL;
        mContext = context;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setPadding(0,0,0,0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Glide.with(mContext).load(imageURL[0] + imageURL[1] + imageURL[position + 2]).into(imageView);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public int getCount() {
        return imageURL.length - 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
