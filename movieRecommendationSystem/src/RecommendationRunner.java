
import java.util.*;

public class RecommendationRunner implements Recommender {

    @Override
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> allFiltered = MovieDatabase.filterBy(new GenreFilter("Comedy"));
        ArrayList<String > moviesIDs = new ArrayList<>();
        int limit = 23;

        if(allFiltered.size() < limit) {
            limit = allFiltered.size();
        }

        for(int i = 0; i < limit; i++) {
            moviesIDs.add(allFiltered.get(i));
        }

        return moviesIDs;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> recommendedMovies = fr.getSimilarRatings(webRaterID, 20, 5);
        if(recommendedMovies.isEmpty()) {
            System.out.println("There was not enough data to create recommended movies list, based on input.");
        } else {
            int counter = 1;
            System.out.println("<html><table>");
                System.out.println("<tr>");
                System.out.println("<th>Movie ID</th>");
                System.out.println("<th>Movie Title</th>");
                System.out.println("<th>Genre</th>");
                System.out.println("<th>Directors</th>");
                System.out.println("<th>Country</th>");
                System.out.println("</tr>");
            for(Rating rating : recommendedMovies) {
                if(counter > 13) {
                    break;
                }
                String movieID = rating.getItem();
                String movieTitle = MovieDatabase.getTitle(movieID);
                String movieGenre = MovieDatabase.getGenres(movieID);
                String movieDirector = MovieDatabase.getDirector(movieID);
                String movieCountry = MovieDatabase.getCountry(movieID);
                System.out.println("<tr>");
                System.out.println("<td>" + movieID + "</td>");
                System.out.println("<td>" + movieTitle + "</td>");
                System.out.println("<td>" + movieGenre + "</td>");
                System.out.println("<td>" + movieDirector + "</td>");
                System.out.println("<td>" + movieCountry + "</td>");
                System.out.println("</tr>");
                counter++;
            }
        }
    }
}
