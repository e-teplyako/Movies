package com.example.android.movies;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.movies.Utilities.DownloadImageTask;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private ArrayList<Movie> _movies;
    private Context _context;

    private SearchRecyclerViewClickListener _listener;

    public SearchAdapter (ArrayList<Movie> movies, Context context, SearchRecyclerViewClickListener listener) {
        _movies = movies;
        _context = context;
        _listener = listener;
    }

    public void setMovies(ArrayList<Movie> movies){
        _movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view, _listener);
        return viewHolder;
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

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context _context;

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
            _context = itemView.getContext();
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
            new DownloadImageTask((_posterImageView)).execute(url);
        }

        @Override
        public void onClick(View view) {
            Movie movie = _movies.get(getAdapterPosition());
            String id = movie.getId();
            _listener.OnClick(view, getAdapterPosition(), id);
        }
    }

    public interface SearchRecyclerViewClickListener {
        void OnClick (View view, int position, String imdbId);
    }

}
