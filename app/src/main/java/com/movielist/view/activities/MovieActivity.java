package com.movielist.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.movielist.R;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.RateDialog;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.User;
import com.movielist.model.entity.moviedetails.Credits;
import com.movielist.model.entity.moviedetails.Movie;
import com.movielist.model.entity.ImagePaths;
import com.movielist.presenter.activity_presenters.MoviePresenter;
import com.movielist.view.RateListener;
import com.movielist.view.adapters.ImageAdapter;
import com.movielist.view.adapters.PersonAdapter;
import com.movielist.view.view_interfaces.MovieView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieActivity extends AppCompatActivity implements MovieView, RateListener {

    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.back)
    ImageView backIcon;

    @BindView(R.id.menu)
    ImageView more;

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

    @BindView(R.id.credits)
    RecyclerView mCredits;

    @BindView(R.id.floatingButton)
    FloatingActionButton mFloatingActionButton;

    private Configuration mConfiguration;
    private MoviePresenter mPresenter;
    private boolean isRated;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int movieID = extras.getInt(Movie.TAG);
            String session = extras.getString(KeyDbHelper.SESSION);
            mConfiguration = (Configuration)extras.getSerializable(Configuration.TAG);
            int userID = extras.getInt(User.USER);
            mPresenter = new MoviePresenter(String.valueOf(movieID), this, new Movie(), session,userID);
        }

        images.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        images.setAdapter(new ImageAdapter(this,new ImagePaths(),mConfiguration));

        mCredits.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        images.setAdapter(new PersonAdapter(this,mConfiguration, new Credits().mCasts));

        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean("Resource")) {
                mFloatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
            }
            else{
                mFloatingActionButton.setImageResource(R.drawable.ic_star_border_white_24dp);
            }
        }

    }

    private void showDialog(){
        RateDialog rateDialog = new RateDialog();
        rateDialog.show(getSupportFragmentManager(),"RateDialog");
    }

    @OnClick(R.id.back)
    void backClick(){
        onBackPressed();
    }

    @OnClick(R.id.menu)
    void menuClick(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.movie_menu,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.add_to_fav:{
                    mPresenter.addToFavourites();
                    break;
                }
                case R.id.add_to_watchlist:{
                    mPresenter.addToWatchlist();
                    break;
                }
            }
            return true;
        });
        popupMenu.show();
    }

    @OnClick(R.id.floatingButton)
    void rateClick(){
        showDialog();
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
        mProgressBar.setVisibility(View.VISIBLE);
        mFloatingActionButton.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
    }

    @Override
    public void setImages(ImagePaths images) {
        this.images.swapAdapter(new ImageAdapter(this,images,mConfiguration),true);
    }

    @Override
    public void setRuntime(String runtime) {
        this.runtime.setText(runtime);
    }

    @Override
    public void setCredits(Credits credits) {
            this.mCredits.swapAdapter(new PersonAdapter(this,mConfiguration,credits.mCasts),true);
    }

    @Override
    public void rate(int rating) {
        mPresenter.rate(rating);
    }

    @Override
    public void onGuest() {
        Toast.makeText(this,"You must be logged in",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        Snackbar snackbar = Snackbar.make(mScrollView.getRootView(),"SUCCESS",3000);
        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
        snackbar.setAction("DISMISS", v -> snackbar.dismiss())
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

    @Override
    public void show() {
        mProgressBar.setVisibility(View.GONE);
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessfullRating() {
        Snackbar snackbar = Snackbar.make(mScrollView.getRootView(),"SUCCESS",3000);
        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
        snackbar.setAction("DISMISS", v -> {
            mPresenter.deleteRating();
            snackbar.dismiss();
            mFloatingActionButton.setImageResource(R.drawable.ic_star_border_white_24dp);
            isRated = false;

        })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .show();
        mFloatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
        isRated = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("Resource", isRated);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

}
