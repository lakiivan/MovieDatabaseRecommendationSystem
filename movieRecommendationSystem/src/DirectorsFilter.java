import java.util.ArrayList;

public class DirectorsFilter implements Filter{
    private ArrayList<String> myDirectors;

    public DirectorsFilter(ArrayList<String> directors) {
        this.myDirectors = directors;
    }

    @Override
    public boolean satisfies(String id) {

        for(String director : myDirectors) {
            if(MovieDatabase.getDirector(id).indexOf(director) != -1) {
                return true;
            }
        }
        return false;
    }
}
