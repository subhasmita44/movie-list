package com.example.movieapplication.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplication.R;
import com.example.movieapplication.model.Search;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isLoading;
    private Activity activity;
    public List<Search> movie;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public MovieAdapter(RecyclerView recyclerView, List<Search> movie, Activity activity) {
        this.movie = movie;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }
    public void setResults(List<Search> movie) {
        this.movie = movie;
        notifyDataSetChanged();
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    // "Normal item" ViewHolder
    private class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView title,type,year,poster,imbd;
        public ImageView img;
        ItemClickListener itemClickListener;
        @Override
        public void onClick(View v) {
           // this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
        public UserViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
           type = (TextView) view.findViewById(R.id.type);

            year = (TextView) view.findViewById(R.id.year);
            imbd=(TextView) view.findViewById(R.id.imbd);
            img = (ImageView) view.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return movie.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_recycler_view_row, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            Search contact = movie.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.title.setText("Title : "+contact.getTitle());
            userViewHolder.type.setText("Type : "+contact.getType());
           userViewHolder.imbd.setText("imbdID : "+contact.getImdbID());
            userViewHolder.year.setText("Year : "+contact.getYear());

            Glide.with(activity).load(contact.getPoster()).into(((UserViewHolder) holder).img);
          //  movieVH.textView.setText(movie.getTitle());
           // userViewHolder.email.setText(contact.getYear());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return movie == null ? 0 : movie.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
  //  In this adapter, declare two constants that is delegate for two item type of RecyclerView:

  //  Provide an OnLoadMoreListener variable and set an "add method":
    private OnLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

}
