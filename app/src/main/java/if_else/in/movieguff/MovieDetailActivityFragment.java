package if_else.in.movieguff;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import if_else.in.movieguff.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent movieDetailIntent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        if(movieDetailIntent != null  && movieDetailIntent.hasExtra(Intent.EXTRA_TEXT)){

            Movie movie = (Movie) movieDetailIntent.getParcelableExtra(Intent.EXTRA_TEXT);

            ((TextView)rootView.findViewById(R.id.movie_detail_title_text)).setText(movie.getTitle());
            ((TextView)rootView.findViewById(R.id.movie_detail_overview_text)).setText(movie.getOverview());


            ImageView imageView = (ImageView)rootView.findViewById(R.id.movie_detail_poster_img);
            Picasso.with(getActivity()).load(movie.getFullPosterPath()).into(imageView);
        }
        return rootView;
    }
}
