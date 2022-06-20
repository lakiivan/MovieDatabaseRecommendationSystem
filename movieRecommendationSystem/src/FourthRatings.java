import java.util.*;

public class FourthRatings {

    public double getAverageByID(String movieID, int minimalRaters) {
        double ratingsSum = 0;
        int counter = 0;
        for(Rater rater : RaterDatabase.getRaters()) {
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

    private int dotProduct(Rater me, Rater r) {
        int dotProduct = 0;
        ArrayList<String> myRatings = me.getItemsRated();
        for(String movieID : myRatings) {
            if(r.hasRating(movieID)) {
                dotProduct += (me.getRating(movieID) - 5) * (r.getRating(movieID) - 5);
            }
        }
        return dotProduct;
    }

    private ArrayList<Rating> getSimilarities(String raterID) {
        ArrayList<Rating> similarRaters = new ArrayList<>();
        Rater me = RaterDatabase.getRater(raterID);
        for(Rater r : RaterDatabase.getRaters()) {
            if(!r.getID().equals(raterID)) {
                double currDotProduct = dotProduct(me, r);
                if(currDotProduct >= 0) {
                    similarRaters.add(new Rating(r.getID(), currDotProduct));
                }
            }

        }
        Collections.sort(similarRaters, Collections.reverseOrder());
        //System.out.println(similarRaters);
        return similarRaters;
    }

    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> recommendedMovies = new ArrayList<>();
        ArrayList<Rating> similarRaters = getSimilarities(raterID);
        for(String movieID : MovieDatabase.filterBy(new TrueFilter())) {
            double weightedRating = 0;
            int counter = 0;
            for(int i = 0; i < numSimilarRaters; i++) {
                String currRaterID = similarRaters.get(i).getItem();
                if(RaterDatabase.getRater(currRaterID).hasRating(movieID)) {
                    double currWeightedRating = similarRaters.get(i).getValue() * RaterDatabase.getRater(currRaterID).getRating(movieID);
                    weightedRating += currWeightedRating;
                    counter++;
                }
            }
            if(counter >= minimalRaters && counter > 0) {
                double avgRating = weightedRating / counter;
                recommendedMovies.add(new Rating(movieID, avgRating));
            }
        }
        Collections.sort(recommendedMovies, Collections.reverseOrder());
        return recommendedMovies;
    }
}
