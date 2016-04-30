package if_else.in.movieguff.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by spatra on 4/28/16.
 */
public class Movie implements Serializable, Parcelable {

    private String title;
    private String overview;
    private String originalTitle;
    private String releaseDate;
    private String posterPath;
    private String fullPosterPath;
    private Float avgRating;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        if(posterPath!=null && !posterPath.trim().equals(""))
            setFullPosterPath(posterPath);
    }

    public String getFullPosterPath() {
        return fullPosterPath;
    }

    public void setFullPosterPath(String posterPath) {


        this.fullPosterPath = "http://image.tmdb.org/t/p/w185"+posterPath;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", posterUrl ='" + fullPosterPath + '\'' +
                ", rating ='" + avgRating + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.originalTitle);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.fullPosterPath);
        dest.writeValue(this.avgRating);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.originalTitle = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.fullPosterPath = in.readString();
        this.avgRating = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
