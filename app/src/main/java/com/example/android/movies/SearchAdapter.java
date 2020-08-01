package com.example.android.movies;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.movies.Utilities.DownloadImageTask;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Movie> _movies;

    private SearchRecyclerViewClickListener _listener;

    public SearchAdapter (ArrayList<Movie> movies, SearchRecyclerViewClickListener listener) {
        _movies = movies;
        _listener = listener;
    }

    public void setMovies(List<Movie> movies){
        _movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup,false);
        return new SearchViewHolder(view, _listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int i) {
        viewHolder.bind(_movies.get(i));
    }

    @Override
    public int getItemCount() {
        if (_movies == null){
            return 0;
        }
        return _movies.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DownloadImageTask.AsyncResponse {
        public TextView _titleTextView;
        public ImageView _posterImageView;
        public TextView _yearTextView;
        public ImageView _typeImageView;

        private SearchRecyclerViewClickListener _listener;

        public SearchViewHolder(@NonNull View itemView, SearchRecyclerViewClickListener listener) {
            super(itemView);
            _titleTextView = itemView.findViewById(R.id.tv_title);
            _yearTextView = itemView.findViewById(R.id.tv_year);
            _typeImageView = itemView.findViewById(R.id.iv_type);
            _posterImageView = itemView.findViewById(R.id.iv_poster);
            _listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            _titleTextView.setText(movie.getTitle());
            _yearTextView.setText(movie.getYear());
            String mediaType = movie.getType();
            if (mediaType.equals("movie")) {
                _typeImageView.setImageResource(R.drawable.movie_art);
            }
            else if (mediaType.equals("series")){
                _typeImageView.setImageResource(R.drawable.tv_art);
            }
            else if (mediaType.equals("game")) {
                _typeImageView.setImageResource(R.drawable.game_art);
            }
            String url = movie.getPosterUrl();
            new DownloadImageTask(this).execute(url);
        }

        @Override
        public void onClick(View view) {
            Movie movie = _movies.get(getAdapterPosition());
            String id = movie.getId();
            _listener.OnClick(view, getAdapterPosition(), id);
        }

        @Override
        public void processFinish(Bitmap output) {
            _posterImageView.setImageBitmap(output);
        }
    }

    public interface SearchRecyclerViewClickListener {
        void OnClick (View view, int position, String imdbId);
    }

}
