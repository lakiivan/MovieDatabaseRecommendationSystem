import java.util.*;

public class MovieRunnerSimilarRatings {

    public static void main(String[] args) {
        MovieRunnerSimilarRatings mr = new MovieRunnerSimilarRatings();
        //mr.printAverageRatings();
        //mr.printAverageRatingsByYearAfterAndGenre();
        //mr.printSimilarRatings();
        //mr.printSimilarRatingsByGenre();
        //mr.printSimilarRatingsByDirector();
        //mr.printSimilarRatingsByGenreAndMinutes();
        mr.printSimilarRatingsByYearAfterAndMinutes();
    }

    public void printAverageRatings() {

        FourthRatings fr = new FourthRatings();
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());
        int minimalRaters = 35;
        ArrayList<Rating> ratings = fr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " meets criteria of " + minimalRaters + " minimal raters.");
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(ratingValue + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        System.out.println();

        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());

        int minimalRaters   = 8;
        int year = 1990;
        String genre = "Drama";

        Filter yearFilter = new YearAfterFilter(year);
        Filter genreFilter = new GenreFilter(genre);
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(yearFilter);
        filters.add(genreFilter);
        ArrayList<Rating> ratings = fr.getAveragesWithMultipleFilters(minimalRaters, filters);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies meets criteria of " + minimalRaters + " minimal raters." + " year: " + year + " genre: " + genre);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String currYear = String.valueOf(MovieDatabase.getYear(movieID));
            System.out.println(ratingValue + " " + currYear + " " +  title);
            System.out.println("\t" + MovieDatabase.getDirector(movieID));
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printSimilarRatings() {

        FourthRatings fr = new FourthRatings();
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());
        int minimalRaters = 5;
        int similarRaters = 20;
        String raterID = "71";
        //System.out.println(RaterDatabase.getRater(raterID).getItemsRated().size());
        ArrayList<Rating> ratings = fr.getSimilarRatings(raterID, similarRaters, minimalRaters);
        System.out.println(ratings.size() + " meets criteria of similar raters number of " +similarRaters+ " and minimal number of raters " + minimalRaters);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(ratingValue + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printSimilarRatingsByGenre() {

        FourthRatings fr = new FourthRatings();
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());
        int minimalRaters = 5;
        int similarRaters = 20;
        String raterID = "964";
        String genre = "Mystery";
        //System.out.println(RaterDatabase.getRater(raterID).getItemsRated().size());
        ArrayList<Rating> allRatings = fr.getSimilarRatings(raterID, similarRaters, minimalRaters);
        ArrayList<Rating> ratings = new ArrayList<>();
        Filter genreFilter = new GenreFilter(genre);
        for(Rating rating : allRatings) {
            String movieId = rating.getItem();
            String genres = MovieDatabase.getGenres(movieId);
            //if(genres.indexOf(genre) != -1) {
            //    ratings.add(rating);
            //}
            if(genreFilter.satisfies(movieId)) {
                ratings.add(rating);
            }
        }
        System.out.println(ratings.size() + " meets criteria of similar raters number of " +similarRaters+ " and minimal number of raters " + minimalRaters);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(ratingValue + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printSimilarRatingsByDirector() {

        FourthRatings fr = new FourthRatings();
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());
        int minimalRaters = 2;
        int similarRaters = 10;
        String raterID = "120";
        ArrayList<String> directors = new ArrayList<>();
        directors.add("Clint Eastwood");
        directors.add("J.J. Abrams");
        directors.add("Alfred Hitchcock");
        directors.add("Sydney Pollack");
        directors.add("David Cronenberg");
        directors.add("Oliver Stone");
        directors.add("Mike Leigh");
        //System.out.println(RaterDatabase.getRater(raterID).getItemsRated().size());
        ArrayList<Rating> allRatings = fr.getSimilarRatings(raterID, similarRaters, minimalRaters);
        ArrayList<Rating> ratings = new ArrayList<>();
        for(Rating rating : allRatings) {
            String movieId = rating.getItem();
            String[] currDirectors = MovieDatabase.getDirector(movieId).split(",");
            for(int i = 0; i < currDirectors.length; i++) {
                if(directors.contains(currDirectors[i])) {
                    ratings.add(rating);
                }
            }
        }
        System.out.println(ratings.size() + " meets criteria of similar raters number of " +similarRaters+ " and minimal number of raters " + minimalRaters);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(ratingValue + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printSimilarRatingsByGenreAndMinutes() {

        FourthRatings fr = new FourthRatings();
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());
        int minimalRaters = 3;
        int similarRaters = 10;
        String raterID = "168";
        String genre = "Drama";
        int minLength = 80;
        int maxLength = 160;
        //System.out.println(RaterDatabase.getRater(raterID).getItemsRated().size());
        ArrayList<Rating> allRatings = fr.getSimilarRatings(raterID, similarRaters, minimalRaters);
        ArrayList<Rating> ratings = new ArrayList<>();
        for(Rating rating : allRatings) {
            String movieId  = rating.getItem();
            String genres   = MovieDatabase.getGenres(movieId);
            int length      = MovieDatabase.getMinutes(movieId);
            if(genres.indexOf(genre) != -1 && (length >= minLength && length <= maxLength)) {
                ratings.add(rating);
            }
        }
        System.out.println(ratings.size() + " meets criteria of similar raters number of " +similarRaters+ " and minimal number of raters " + minimalRaters);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            String minutes = String.valueOf(MovieDatabase.getMinutes(movieID));
            System.out.println(ratingValue + " " + minutes + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }


    public void printSimilarRatingsByYearAfterAndMinutes() {

        FourthRatings fr = new FourthRatings();
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + RaterDatabase.getRaters().size());
        int minimalRaters   = 5;
        int similarRaters   = 10;
        String raterID      = "314";
        int year            = 1975;
        int minLength       = 70;
        int maxLength       = 200;
        //System.out.println(RaterDatabase.getRater(raterID).getItemsRated().size());
        ArrayList<Rating> allRatings    = fr.getSimilarRatings(raterID, similarRaters, minimalRaters);
        ArrayList<Rating> ratings       = new ArrayList<>();
        Filter yearFilter       = new YearAfterFilter(year);
        Filter minutesFilter    = new MinutesFilter(minLength, maxLength);
        for(Rating rating : allRatings) {
            String movieID  = rating.getItem();
            if(yearFilter.satisfies(movieID) && minutesFilter.satisfies(movieID)) {
                ratings.add(rating);
            }
        }
        System.out.println(ratings.size() + " meets criteria of similar raters number of " +similarRaters+ " and minimal number of raters " + minimalRaters);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            String myear = String.valueOf(MovieDatabase.getYear(movieID));
            String mlength = String.valueOf(MovieDatabase.getMinutes(movieID));
            System.out.println(ratingValue + ", " + myear + ", " + mlength + ", " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }
}
