package com.movielist.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movielist.R;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.moviedetails.MovieDetailed;
import com.movielist.presenter.activity_presenters.MoviePresenter;
import com.movielist.view.view_interfaces.MovieView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieActivity extends AppCompatActivity implements MovieView {

    @BindView(R.id.poster)
    ImageView poster;

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

    private Configuration mConfiguration;
    private MoviePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int movieID = extras.getInt(MovieDetailed.TAG);
            mConfiguration = (Configuration)extras.getSerializable(Configuration.TAG);
            mPresenter = new MoviePresenter(String.valueOf(movieID),this,new MovieDetailed());
        }

    }

    @OnClick(R.id.back)
    public void backClick(){
        onBackPressed();
        finish();
    }
    @Override
    public void addRating(String rating) {
        this.rating.setText(rating);
    }

    @Override
    public void addTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void addDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void addGenres(String genres) {
        this.genres.setText(genres);
    }

    @Override
    public void setPoster(String posterPath) {

        if(posterPath != null) {
            Glide.with(this).load(Uri.parse(
                    mConfiguration.getImageConfig().getSecureBaseUrl()
                            + mConfiguration.getImageConfig().getPosterSizes()[4]
                            + posterPath))
                    .into(poster);
            return;
        }
        poster.setImageResource(R.drawable.blur60);

    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void setRuntime(String runtime) {
        this.runtime.setText(runtime);
    }

    @Override
    public void onError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
