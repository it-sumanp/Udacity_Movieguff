package if_else.in.movieguff.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import if_else.in.movieguff.R;
import if_else.in.movieguff.model.Movie;

public class MovieTileGridAdapter extends BaseAdapter{

    private Context mContext;
    private List<Movie> moviesList = new ArrayList<Movie>();

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    public MovieTileGridAdapter(Context c,List<Movie> movieList) {
        mContext = c;
        this.moviesList = movieList;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Movie getItem(int position) {
        return  moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            Log.e("ADAPTER ::: ","creating view for " + moviesList.get(position).toString());
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.movie_tile, null);

            TextView textView = (TextView) grid.findViewById(R.id.movie_tile_text);
            textView.setText(moviesList.get(position).getTitle());

            ImageView imageView = (ImageView)grid.findViewById(R.id.movie_tile_img);
            Picasso.with(mContext).load(moviesList.get(position).getFullPosterPath()).into(imageView);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
