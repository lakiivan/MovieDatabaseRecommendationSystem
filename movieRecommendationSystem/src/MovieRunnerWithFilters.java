import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

    public static void main(String[] args) {
        MovieRunnerWithFilters mr = new MovieRunnerWithFilters();
        mr.printAverageRatings();
        //mr.printAverageRatingsByYear();
        //mr.printAverageRatingsByGenre();
        //mr.printAverageRatingsByMinutes();
        //mr.printAverageRatingsByDirectors();
        mr.printAverageRatingsByYearAfterAndGenre();
        System.out.println("*******************************************************************************************");
        //mr.printAverageRatingsByDirectorsAndMinutes ();
    }

    public void printAverageRatings() {
        //SecondRatings tr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
        //SecondRatings tr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());
        int minimalRaters = 35;
        ArrayList<Rating> ratings = tr.getAverageRatings(minimalRaters);
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

    public void printAverageRatingsByYear() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());
        int minimalRaters   = 20;
        int year            = 2000;
        Filter yearsFilter = new YearAfterFilter(year);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, yearsFilter);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " meets criteria of " + minimalRaters + " minimal raters.");
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String currYear = String.valueOf(MovieDatabase.getYear(movieID));
            System.out.println(ratingValue + " " + currYear + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printAverageRatingsByGenre() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());
        int minimalRaters   = 20;
        String genre = "Comedy";
        Filter genreFilter = new GenreFilter(genre);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, genreFilter);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies meets criteria of " + minimalRaters + " minimal raters." + " genre: " + genre);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String currYear = String.valueOf(MovieDatabase.getYear(movieID));
            System.out.println(ratingValue + " " + currYear + " " +  title);
            System.out.println("\t" + MovieDatabase.getGenres(movieID));
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printAverageRatingsByMinutes () {
        System.out.println();
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());
        int minimalRaters   = 5;
        int minMinutes      = 105;
        int maxMinutes      = 135;
        Filter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, minutesFilter);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies meets criteria of " + minimalRaters + " minimal raters." + " minutes: " + minMinutes + " - " + maxMinutes);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String minutes = String.valueOf(MovieDatabase.getMinutes(movieID));
            System.out.println(ratingValue + " Time:" + minutes + " " +  title);
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printAverageRatingsByDirectors () {
        System.out.println();
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());
        int minimalRaters   = 4;
        ArrayList<String> directors = new ArrayList<>();
        directors.add("Clint Eastwood");
        directors.add("Joel Coen");
        directors.add("Martin Scorsese");
        directors.add("Roman Polanski");
        directors.add("Nora Ephron");
        directors.add("Ridley Scott");
        directors.add("Sydney Pollack");
        Filter directorsFilter = new DirectorsFilter(directors);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, directorsFilter);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies meets criteria of " + minimalRaters + " minimal raters." + " director: " + directors);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String currYear = String.valueOf(MovieDatabase.getYear(movieID));
            System.out.println(ratingValue + " " +  " " +  title);
            System.out.println("\t" + MovieDatabase.getDirector(movieID));
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        System.out.println();
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());

        int minimalRaters   = 8;
        int year = 1990;
        String genre = "Drama";

        Filter yearFilter = new YearAfterFilter(year);
        Filter genreFilter = new GenreFilter(genre);
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(yearFilter);
        filters.add(genreFilter);
        ArrayList<Rating> ratings = tr.getAveragesWithMultipleFilters(minimalRaters, filters);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies meets criteria of " + minimalRaters + " minimal raters." + " year: " + year + " genre: " + genre);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String currYear = String.valueOf(MovieDatabase.getYear(movieID));
            System.out.println(ratingValue + " " +  " " +  title);
            System.out.println("\t" + MovieDatabase.getDirector(movieID));
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }

    public void printAverageRatingsByDirectorsAndMinutes () {
        System.out.println();
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Movies number in db: " + MovieDatabase.size());
        System.out.println("Raters number in db: " + tr.getRaterSize());

        int minimalRaters   = 3;
        int minMinutes = 90;
        int maxMinutes = 180;
        ArrayList<String> directors = new ArrayList<>();
        directors.add("Clint Eastwood");
        directors.add("Joel Coen");
        directors.add("Tim Burton");
        directors.add("Ron Howard");
        directors.add("Nora Ephron");
        directors.add("Sydney Pollack");

        Filter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        Filter directorsFilter = new DirectorsFilter(directors);
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(minutesFilter);
        filters.add(directorsFilter);
        ArrayList<Rating> ratings = tr.getAveragesWithMultipleFilters(minimalRaters, filters);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies meets criteria of " + minimalRaters + " minimal raters." + " minutes: " + minMinutes + " - " + maxMinutes + " directors: " + directors);
        for(Rating rating : ratings) {
            double ratingValue = rating.getValue();
            String movieID  = rating.getItem();
            String title    = MovieDatabase.getTitle(movieID);
            String minutes = String.valueOf(MovieDatabase.getMinutes(movieID));
            System.out.println(ratingValue + " " +  " minutes " + minutes + " " +  title);
            System.out.println("\t" + MovieDatabase.getDirector(movieID));
        }
        System.out.println("Movies with more then " + minimalRaters + " ratings: " + ratings.size());
    }
}
