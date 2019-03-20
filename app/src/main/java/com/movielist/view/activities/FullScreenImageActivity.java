package com.movielist.view.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.movielist.R;
import com.movielist.model.entity.moviedetails.MovieImages;
import com.movielist.view.adapters.FullScreenImageAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenImageActivity extends AppCompatActivity {

    @BindView(R.id.image_pager)
    ViewPager pager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_image_activity);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String[] images = extras.getStringArray(MovieImages.TAG);
            int pos = extras.getInt("pos");
            FullScreenImageAdapter adapter = new FullScreenImageAdapter(images,this);
            pager.setAdapter(adapter);
            pager.setCurrentItem(pos);
        }
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mToolbar.inflateMenu(R.menu.toolbar_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_image,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
