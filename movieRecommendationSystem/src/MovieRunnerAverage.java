
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage{
    
    public MovieRunnerAverage() {
        // default constructor
    }

    public static void main(String[] args) {
        MovieRunnerAverage mr = new MovieRunnerAverage();
        mr.printAverageRatings();
        //mr.getAverageRatingOneMovie();
    }

    public void printAverageRatings() {
        //SecondRatings tr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
        SecondRatings tr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        //ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());
        int minimalRaters = 12;
        ArrayList<Rating> ratings = tr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " meets criteria of " + minimalRaters + " minimal raters.");
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(ratingValue + " " +  title);
        }
        System.out.println("Movies with more then 50 ratings: " + ratings.size());
    }

    public void getAverageRatingOneMovie() {
        System.out.println();
        //SecondRatings sr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        //PRACTICE
        //String movieTitle = "The Godfather";
        //quiz5
        //String movieTitle = "The Maze Runner";
        //quiz6
        //String movieTitle = "Moneyball";
        //quiz7
        String movieTitle = "Vacation";
        String movieID = sr.getID(movieTitle);
        double averageRating = sr.getAverageByID(movieID, 1);
        System.out.println(averageRating + " " + movieTitle);
    }
}
