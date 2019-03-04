package com.example.android.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public SearchAdapter (ArrayList<Movie> movies, Context context) {
        mMovies = movies;
        mContext = context;
    }

    public void setMovies(ArrayList<Movie> movies){
        mMovies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int i) {
        Movie movie = mMovies.get(i);
        viewHolder.mTitleTextView.setText(movie.getTitle() + " (" + movie.getYear() + ")");
//        IMAGE NOT IMPLEMENTED YET
    }

    @Override
    public int getItemCount() {
        if (mMovies == null){
            return 0;
        }
        return mMovies.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        public TextView mTitleTextView;
        public ImageView mPosterImageView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.tv_title);
            mContext = itemView.getContext();
        }
    }
}
