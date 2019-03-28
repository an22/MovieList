package com.movielist.view.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movielist.R;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.tvdetails.TV;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class TvActivity extends AppCompatActivity {

    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.back)
    ImageView backIcon;

    @BindView(R.id.rating)
    TextView rating;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.images)
    RecyclerView images;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.genres)
    TextView genres;

    @BindView(R.id.runtime)
    TextView runtime;

    @BindView(R.id.floatingButton)
    FloatingActionButton mFloatingActionButton;

    private Configuration mConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int tvID = extras.getInt(TV.TAG);
            mConfiguration = (Configuration)extras.getSerializable(Configuration.TAG);

        }

        images.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    }
}
