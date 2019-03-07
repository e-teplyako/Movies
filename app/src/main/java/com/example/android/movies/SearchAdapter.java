package com.example.android.movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.Utilities.DownloadImageTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    private SearchRecyclerViewClickListener mListener;

    public SearchAdapter (ArrayList<Movie> movies, Context context, SearchRecyclerViewClickListener listener) {
        mMovies = movies;
        mContext = context;
        mListener = listener;
    }

    public void setMovies(ArrayList<Movie> movies){
        mMovies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int i) {
        Movie movie = mMovies.get(i);
        viewHolder.mTitleTextView.setText(movie.getTitle());
        viewHolder.mYearTextView.setText(movie.getYear());
        String mediaType = movie.getType();
        if (mediaType.equals("movie")) {
            viewHolder.mTypeImageView.setImageResource(R.drawable.movie_art);
        }
        else if (mediaType.equals("series")){
            viewHolder.mTypeImageView.setImageResource(R.drawable.tv_art);
        }
        else if (mediaType.equals("game")) {
            viewHolder.mTypeImageView.setImageResource(R.drawable.game_art);
        }
        String url = movie.getPosterUrl();
        new DownloadImageTask((viewHolder.mPosterImageView)).execute(url);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null){
            return 0;
        }
        return mMovies.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;

        public TextView mTitleTextView;
        public ImageView mPosterImageView;
        public TextView mYearTextView;
        public ImageView mTypeImageView;

        private SearchRecyclerViewClickListener mListener;

        public SearchViewHolder(@NonNull View itemView, SearchRecyclerViewClickListener listener) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.tv_title);
            mYearTextView = itemView.findViewById(R.id.tv_year);
            mTypeImageView = itemView.findViewById(R.id.iv_type);
            mPosterImageView = itemView.findViewById(R.id.iv_poster);
            mContext = itemView.getContext();
            mListener = listener;
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            Movie movie = mMovies.get(getAdapterPosition());
            String id = movie.getId();
            mListener.OnClick(view, getAdapterPosition(), id);
        }
    }

    public interface SearchRecyclerViewClickListener {
        void OnClick (View view, int position, String imdbId);
    }

}
