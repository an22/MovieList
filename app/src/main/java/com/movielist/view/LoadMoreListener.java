package com.movielist.view;

import com.movielist.model.model_interfaces.Loadable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoadMoreListener extends RecyclerView.OnScrollListener {

    private Loadable mLoadable;
    private int loadWhenLeft;

    public LoadMoreListener(Loadable loadable, int loadWhenLeft){
        mLoadable = loadable;
        this.loadWhenLeft = loadWhenLeft;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            int itemCount = recyclerView.getLayoutManager().getItemCount();
            int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            if (itemCount - lastVisibleItem <= loadWhenLeft) {
                mLoadable.loadMore();
            }
        }
    }
}
