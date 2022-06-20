/**
 * Write a description of ThirdRatings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;

    public ThirdRatings() {
        // default constructor
        this( "ratings.csv");
    }

    public ThirdRatings( String ratersFile) {
        this.myRaters  = new ArrayList<>();

        FirstRatings fr = new FirstRatings();
        this.myRaters  = fr.loadRaters(ratersFile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String  movie : movies) {
            double averageRating = getAverageByID(movie, minimalRaters);
            if(averageRating != 0) {
                averageRatings.add(new Rating(movie, averageRating));
            }
        }
        return averageRatings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> averageRatings = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String  movie : movies) {
            if(filterCriteria.satisfies(movie)){
                double averageRating = getAverageByID(movie, minimalRaters);
                if(averageRating != 0) {
                    averageRatings.add(new Rating(movie, averageRating));
                }
            }
        }
        return averageRatings;
    }
    public ArrayList<Rating> getAveragesWithMultipleFilters(int minimalRaters, ArrayList<Filter> filters) {
        ArrayList<Rating> averageRatings = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String  movie : movies) {
            boolean filtersPassed = true;
            for(Filter filter : filters) {
                if(!filter.satisfies(movie)){
                    filtersPassed = false;
                }
            }
            if(filtersPassed) {
                double averageRating = getAverageByID(movie, minimalRaters);
                if(averageRating != 0) {
                    averageRatings.add(new Rating(movie, averageRating));
                }
            }
        }
        return averageRatings;
    }

}
