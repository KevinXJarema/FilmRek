import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieRecommendationApp {
    public static void main(String[] args) {
        // Create a list of movies by scraping data from a website


        // Get user preferences (genre)
        String userPreference = getUserPreference();



        // Recommend movies based on the user's genre preference
        List<Movie> Movies = scrapeMovieData("https://www.imdb.com/search/title/?genres="+userPreference+"&explore=genres&title_type=movie");
        for (Movie movie : Movies) {
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Rating: " + movie.getRating());
            System.out.println("Genre: " + movie.getGenre());
            System.out.println();
        }
    }

    private static List<Movie> scrapeMovieData(String url) {
        List<Movie> movies = new ArrayList<>();

        try {
            int page = 0;
            while (page < 1) {
                Document doc = Jsoup.connect(url).get();
                Elements movieElements = doc.select("div.lister-item.mode-advanced");
                String output;
                for (Element element : movieElements) {
                    String title = element.select("h3 a").text();
                    String rating = element.select("strong").text();
                    String genre = element.select("span.genre").text();

                    Movie movie = new Movie(title, rating, genre);
                    movies.add(movie);
                }
            page++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return movies;
    }
    private static String getUserPreference() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your movie genre preference: ");
        return scanner.nextLine().toLowerCase();
    }


}