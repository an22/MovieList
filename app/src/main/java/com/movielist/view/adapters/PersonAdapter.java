package com.movielist.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movielist.R;
import com.movielist.model.entity.Configuration;
import com.movielist.model.model_interfaces.Credit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    private Configuration mConfiguration;
    private Context mContext;
    private Credit[] mCast;

    public PersonAdapter(Context context, Configuration configuration, Credit[] cast){
        this.mContext = context;
        this.mConfiguration = configuration;
        this.mCast = cast;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.credit_view,parent,false);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCast.length;
    }

    class PersonHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster)
        ImageView poster;

        @BindView(R.id.title)
        TextView name;

        @BindView(R.id.role)
        TextView role;

        PersonHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(int pos){
            name.setText(mCast[pos].getName());
            role.setText(mCast[pos].getRole());
            if(mCast[pos].getProfilePath()!= null) {
                Glide.with(mContext).load(
                        mConfiguration.getImageConfig().getSecureBaseUrl() +
                                mConfiguration.getImageConfig().getPosterSizes()[3] +
                                mCast[pos].getProfilePath()).into(poster);
            }else{
                poster.setImageResource(R.drawable.blur60);
            }
        }
    }
}
