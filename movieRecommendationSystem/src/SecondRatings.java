/**
 * Write a description of SecondRatings here.
 * Second ratings is a class that has two fields arraylist of rated movies and raters 
 * It loads all movies and raters using FirstRatings Class
 * @author (Ivan Lazic) 
 * @version (v1)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviesFile, String ratersFile) {
        this.myMovies  = new ArrayList<>();
        this.myRaters  = new ArrayList<>();
        
        FirstRatings fr = new FirstRatings();
        this.myMovies  = fr.loadMovies(moviesFile);
        this.myRaters  = fr.loadRaters(ratersFile);
    }
    
    public int getMovieSize() {
        return this.myMovies.size();
    }
    
    public int getRaterSize() {
        return this.myRaters.size();
    }

    public double getAverageByID(String movieID, int minimalRaters) {
        double ratingsSum = 0;
        int counter = 0;
        for(Rater rater : this.myRaters) {
            if(rater.hasRating(movieID)) {
                ratingsSum += rater.getRating(movieID);
                counter++;
            }
        }
        if(counter != 0 && counter >= minimalRaters) {
            return ratingsSum / counter;
        }
        return 0.0;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> averageRatings = new ArrayList<>();
        for(Movie movie : myMovies) {
            String movieId = movie.getID();
            double averageRating = getAverageByID(movieId, minimalRaters);
            if(averageRating != 0) {
                averageRatings.add(new Rating(movieId, averageRating));
            }
        }
        return averageRatings;
    }

    public String getTitle(String movieID) {
        String outcome = "Movie ID " + movieID + " was not found";
        for(Movie movie : myMovies) {
            if(movie.getID().equals(movieID)) {
                return movie.getTitle();
            }
        }
        return outcome;
    }

    public String getID(String title) {
        String movieID = "NO SUCH TITLE";
        for(Movie movie : myMovies) {
            if(movie.getTitle().equals(title)) {
                return movie.getID();
            }
        }
        return movieID;
    }

}
