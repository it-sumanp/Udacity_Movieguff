package if_else.in.movieguff.util;

import java.util.List;

import if_else.in.movieguff.model.Movie;

/**
 * Created by spatra on 4/28/16.
 */
public interface AsyncMoviesResponce {
    void asyncMovieApiTaskhFinish(List<Movie> movieList);
}
