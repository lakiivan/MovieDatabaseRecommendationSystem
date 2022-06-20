import edu.duke.FileResource;
import org.apache.commons.csv.*;

import javax.naming.directory.ModificationItem;
import java.util.ArrayList;
import java.util.HashMap;

public class FirstRatings {

    public ArrayList<Movie> loadMovies(String fileName) {
        ArrayList<Movie> newMoives = new ArrayList<>();
        FileResource fr = new FileResource(fileName);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record: parser) {
            //System.out.println(record);
            Movie movie = new Movie(record.get("id"), record.get("title"), record.get("year"), record.get("genre"),
                    record.get("director"), record.get("country"), record.get("poster"),
                    Integer.parseInt(record.get("minutes")));
            newMoives.add(movie);
        }
        return newMoives;
    }

    public ArrayList<Movie> filterByGenre(String fileName, String genre) {
        ArrayList<Movie> allMovies = loadMovies(fileName);
        ArrayList<Movie> genreFilteredMovies = new ArrayList<>();
        for(Movie m : allMovies) {
            if(m.getGenres().equals(genre)) {
                genreFilteredMovies.add(m);
            }
        }
        return genreFilteredMovies;
    }

    public ArrayList<Movie> filterByLength(String fileName, int minutes) {
        ArrayList<Movie> allMovies = loadMovies(fileName);
        ArrayList<Movie> LengthFilteredMovies = new ArrayList<>();
        for(Movie m : allMovies) {
            //System.out.println(m.getMinutes());
            if(m.getMinutes() > minutes) {
                LengthFilteredMovies.add(m);
            }
        }
        return LengthFilteredMovies;
    }

    public HashMap<String, ArrayList<Movie>> getMoviesByDirectorMap(String fileName) {
        ArrayList<Movie> allMovies = loadMovies(fileName);
        HashMap<String, ArrayList<Movie>> moviesByDirectorMap = new HashMap<>();
        for(Movie m : allMovies) {
            String[] directors = m.getDirector().split(",");
            for(String director : directors) {
                if(!moviesByDirectorMap.containsKey(director)) {
                    ArrayList<Movie> currMovies = new ArrayList<>();
                    moviesByDirectorMap.put(director, currMovies);
                } else {
                    ArrayList<Movie> currMovies = moviesByDirectorMap.get(director);
                    currMovies.add(m);
                    moviesByDirectorMap.put(director, currMovies);
                }
            }
        }
        return moviesByDirectorMap;
    }

    public int filterByMaxNumMoviesByDirector(String fileName) {
        HashMap<String, ArrayList<Movie>> directorsMovieMap = getMoviesByDirectorMap(fileName);
        int maxNumberOfMovies = 0;
        String director = "";
        for(String currDirector : directorsMovieMap.keySet()) {
            int currNumberOfMovies = directorsMovieMap.get(currDirector).size();
            if(currNumberOfMovies > maxNumberOfMovies) {
                maxNumberOfMovies = currNumberOfMovies;
                director = currDirector;
            }
        }
        System.out.println(director);
        return maxNumberOfMovies;
    }

    public ArrayList<Rater> loadRaters(String fileName) {
        ArrayList<Rater> newRaters = new ArrayList<>();
        FileResource fr = new FileResource(fileName);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record: parser) {
            //System.out.println(record);
            String raterId = record.get("rater_id");
            int index= findRater(newRaters, raterId);
            if(index == -1) {
                Rater currRater = new EfficientRater(record.get("rater_id"))  ;
                currRater.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
                newRaters.add(currRater);
            } else {
                Rater currRater = newRaters.get(index);
                currRater.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
                newRaters.set(index, currRater);
            }
        }
        return newRaters;
    }

    private int findRater(ArrayList<Rater> raters, String raterId) {
        for(int i = 0; i < raters.size(); i++) {
            if(raters.get(i).getID().equals(raterId)) {
                return i;
            }
        }
        return -1;
    }

    public int findNumberOfRatings(String rater_id) {
        //ArrayList<Rater> raters = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        int numberOfRatings = 0;
        for(Rater r : raters) {
            if(r.getID().equals(rater_id)) {
                numberOfRatings = r.getItemsRated().size();
            }
        }
        return numberOfRatings;
    }

    public ArrayList<Rater> findRaterWithMaxNumberOfRatings() {
        //ArrayList<Rater> raters = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        ArrayList<Rater> maxRaters = new ArrayList<>();
        int max = 0;
        int maxIndex = -1;
        for(int i = 0; i < raters.size(); i++) {
            int currNoOfRatings = raters.get(i).getItemsRated().size();
            if(currNoOfRatings > max) {
                max = currNoOfRatings;
                maxIndex = i;
            }
        }
        if(maxIndex != -1) {
            for(int i = 0; i < raters.size(); i++) {
                if(raters.get(i).getItemsRated().size() == max) {
                    maxRaters.add(raters.get(i));
                }
            }
        }
        //System.out.println("max ratings by any user " + max);
        return maxRaters;
    }

    public int findMovieRatingsNumber(String movieId) {
        //ArrayList<Rater> raters = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        int numberOfRatings = 0;
        for(int i = 0; i < raters.size(); i++) {
            if(raters.get(i).hasRating(movieId)) {
                numberOfRatings++;
            }
        }
        return numberOfRatings;
    }

    public ArrayList<String> findHowManyMoviesAreRated() {
        //ArrayList<Rater> raters = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        ArrayList<String> ratedMovies = new ArrayList<>();
        int numberOfRatings = 0;
        for(int i = 0; i < raters.size(); i++) {
            ArrayList<String> currRated = raters.get(i).getItemsRated();
            for(String movieId : currRated) {
                if(!ratedMovies.contains(movieId)) {
                    ratedMovies.add(movieId);
                }
            }
        }
        return ratedMovies;
    }

}