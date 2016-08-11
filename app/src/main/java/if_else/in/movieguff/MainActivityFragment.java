package if_else.in.movieguff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import if_else.in.movieguff.model.Movie;
import if_else.in.movieguff.util.AsyncMoviesResponce;
import if_else.in.movieguff.util.MovieApiUtil;
import if_else.in.movieguff.util.MovieTileGridAdapter;

public class MainActivityFragment extends Fragment implements AsyncMoviesResponce {

    static String sortOdrer;
    MovieTileGridAdapter mMoviesAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.e("MainActivityFragment","MainActivity :: onCreate ");
        super.onCreate(savedInstanceState);
        updateMovies();
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        Log.e("mainActivityFragment", "onResume :: start");
        super.onResume();

        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("mainActivityFragment", "onStart :: start");

        updateMovies();
        mMoviesAdapter.notifyDataSetChanged();
        Log.e("mainActivityFragment", "onStart :: end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mMoviesAdapter =
        new MovieTileGridAdapter(getActivity(), new ArrayList<Movie>());

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);
        gridView.setAdapter(mMoviesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMoviesAdapter.getItem(position);
                Toast.makeText(getActivity(), movie.getTitle() + " :: " + movie.getFullPosterPath(), Toast.LENGTH_SHORT).show();

                Intent movieDetailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                movieDetailIntent.putExtra(Intent.EXTRA_TEXT, (Parcelable) movie);
                startActivity(movieDetailIntent);

            }
        });
        return rootView;
    }


    @Override
    public void asyncMovieApiTaskhFinish(List<Movie> movieList) {

        Log.e("LISTING_MOVIES:::", "LISTING_MOVIES:::LISTING_MOVIES:::LISTING_MOVIES:::LISTING_MOVIES:::");
        if(movieList != null && movieList.size() > 0){
            Log.e("LISTING_MOVIES###", "LISTING_MOVIES###LISTING_MOVIES###LISTING_MOVIES###LISTING_MOVIES###");



            GridView gridView = (GridView) getActivity().findViewById(R.id.movie_grid_view);


            Log.e("LIST ;; ", "***************************************************************************************************************");
            Log.e("LIST ;; ", ((MovieTileGridAdapter) gridView.getAdapter()).getMoviesList().toString());
            Log.e("LIST ;; ", "***************************************************************************************************************");

            ((MovieTileGridAdapter) gridView.getAdapter()).getMoviesList().clear();
            ((MovieTileGridAdapter) gridView.getAdapter()).getMoviesList().addAll(movieList);

            Log.e("LIST ;; ", "###################################################################################################################");
            Log.e("LIST ;; ", ((MovieTileGridAdapter) gridView.getAdapter()).getMoviesList().toString());
            Log.e("LIST ;; ", "###################################################################################################################");

            ((MovieTileGridAdapter) gridView.getAdapter()).notifyDataSetChanged();


//  mMoviesAdapter.getMoviesList().addAll(movieList);



//            mMoviesAdapter.notifyDataSetChanged();

            Log.e("LISTING_MOVIES@@@", "LISTING_MOVIES@@@LISTING_MOVIES@@@LISTING_MOVIES@@@LISTING_MOVIES@@@");
        }
    }

    private void updateMovies(){
        MovieApiUtil movieApiUtil = new MovieApiUtil(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = preferences.getString(getString(R.string.sort_order_key), getString(R.string.app_setting_default_value));
        if (sortOdrer != null || !sortBy.equalsIgnoreCase(sortOdrer)) {
            sortOdrer = sortBy;
            movieApiUtil.execute(sortBy);
        }
    }
}
