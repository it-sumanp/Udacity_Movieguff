package if_else.in.movieguff.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import if_else.in.movieguff.model.Movie;

/**
 * Created by spatra on 4/28/16.
 */
public class MovieApiUtil extends AsyncTask<String, Void, List<Movie>> {

    private final String LOG_TAG = MovieApiUtil.class.getSimpleName();

    private AsyncMoviesResponce delegate;

    public MovieApiUtil(AsyncMoviesResponce delegate) {
        this.delegate = delegate;
    }

    public MovieApiUtil() {

    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        Log.e(LOG_TAG, "api calling");

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieApiCallresponce = null;

        int numberOfResponce = 13;

        try {
            final String MOVIE_BASE_URL =
                    "https://api.themoviedb.org/3/movie";
            final String PATH_PARAM_POPULAR = "/popular";
            final String PATH_PARAM_RATING = "/top_rated";
            final String APPID_PARAM = "api_key";

            String apiURL;
            if ("popularity".equalsIgnoreCase(params[0])) {
                apiURL = MOVIE_BASE_URL + PATH_PARAM_POPULAR;
                Log.e("SORT_BY","###########"+params[0]);
            } else if ("rating".equalsIgnoreCase(params[0])) {
                apiURL = MOVIE_BASE_URL + PATH_PARAM_RATING;
                Log.e("SORT_BY","############"+params[0]);
            } else {
                apiURL = MOVIE_BASE_URL + PATH_PARAM_POPULAR;
            }

            Uri builtUri = Uri.parse(apiURL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, "e40bcb18bc64437dbd793d31d46c724f")
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieApiCallresponce = buffer.toString();

            Log.v(LOG_TAG, "movie api responce string: " + movieApiCallresponce);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDetailsFromJson(movieApiCallresponce, numberOfResponce);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return new ArrayList<Movie>();
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        delegate.asyncMovieApiTaskhFinish(movieList);
    }

    private List<Movie> getMovieDetailsFromJson(String moviesJsonStr, int numberOfResponce)
            throws JSONException {

        final String MOVIE_RESULTS = "results";
        final String MOVIE_TITLE = "title";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";


        List<Movie> movieList = new ArrayList<Movie>();

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_RESULTS);


        String[] resultStrs = new String[numberOfResponce];
        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject movie = moviesArray.getJSONObject(i);

            Movie movieObj = new Movie();
            movieObj.setTitle(movie.getString(MOVIE_TITLE));
            movieObj.setPosterPath(movie.getString(MOVIE_POSTER_PATH));
            movieObj.setAvgRating(new Double(movie.getDouble(MOVIE_VOTE_AVERAGE)).floatValue());
            movieObj.setOriginalTitle(movie.getString(MOVIE_ORIGINAL_TITLE));
            movieObj.setOverview(movie.getString(MOVIE_OVERVIEW));
            movieObj.setReleaseDate(movie.getString(MOVIE_RELEASE_DATE));

            movieList.add(movieObj);
        }

        Log.v("All Movie Detali :: ", movieList.toString());
        return movieList;

    }
}