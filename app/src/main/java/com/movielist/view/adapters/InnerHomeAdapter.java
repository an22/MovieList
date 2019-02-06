package com.movielist.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movielist.R;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.model.entity.catalog.User;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.GetImageCfg;

import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InnerHomeAdapter extends RecyclerView.Adapter<InnerHomeAdapter.InnerViewHolder> implements Observer {

    private MovieResult movies;
    private User user;
    private int page;
    private Configuration mConfiguration;
    private boolean allow = false;
    private Context mContext;

    InnerHomeAdapter(User user, Context context) {
        this.movies = new MovieResult();
        this.movies.addObserver(this);
        this.mContext = context;
        this.user = user;
        this.page = 1;
        loadConfig();
    }

    void setPosition(int pos){
        switch (pos){
            case 0:{
                movies.loadUpcoming(user,page);
                break;
            }
            case 1:{
                movies.loadPopular(user,page);
                break;
            }
            case 2:{
                movies.loadTopRated(user,page);
                break;
            }
        }
    }


    private void loadConfig(){
        GetImageCfg cfg = RetrofitSingleton.getInstance().getRetrofit().create(GetImageCfg.class);
        cfg.getCfg(TmdbConstants.keyV3).enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                Configuration config = response.body();
                System.out.println(response.toString());
                if(config != null){
                    mConfiguration = config;
                    allow = true;
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
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

    @Override
    public void update(Observable o, Object arg) {
        notifyDataSetChanged();
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
            if(allow){
                Glide.with(mContext).load(mConfiguration.getImageConfig().getSecureBaseUrl() + mConfiguration.getImageConfig().getPosterSizes()[3] + movies.getShorts().get(pos).getPosterPath()).into(poster);
            }
        }
    }
}
