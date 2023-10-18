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
        while (true) {
            // Get user preferences (genre)
            String userPreference = getUserPreference();

            if ("exit".equalsIgnoreCase(userPreference)) {
                System.out.println("Exiting the application.");
                break; // Exit the loop
            }

            if (userPreference != null && !userPreference.isEmpty()) {
                List<Movie> movies = scrapeMovieData("https://www.imdb.com/search/title/?genres=" + userPreference + "&explore=genres&title_type=movie");
                if (movies != null && !movies.isEmpty()) {
                    for (Movie movie : movies) {
                        System.out.println("Title: " + movie.getTitle());
                        System.out.println("Rating: " + movie.getRating());
                        System.out.println("Genre: " + movie.getGenre());
                        System.out.println();
                    }
                } else {
                    System.out.println("No movies found for the specified genre.");
                }
            } else {
                System.out.println("Invalid user preference.");
            }
        }
    }

    private static List<Movie> scrapeMovieData(String url) {
        List<Movie> movies = new ArrayList<>();
        int maxPages = 1; // Adjust the number of pages to scrape

        try {
            for (int page = 1; page <= maxPages; page++) {
                Document doc = Jsoup.connect(url + "&page=" + page).get();
                Elements movieElements = doc.select("div.lister-item.mode-advanced");

                if (movieElements.isEmpty()) {
                    break; // No more movies on subsequent pages
                }

                for (Element element : movieElements) {
                    String title = element.select("h3 a").text();
                    String rating = element.select("strong").text();
                    String genre = element.select("span.genre").text();

                    Movie movie = new Movie(title, rating, genre);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while scraping movie data.");
        }
        return movies;
    }

    private static String getUserPreference() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your movie genre preference (enter 'exit' to quit): ");
        return scanner.nextLine().toLowerCase();
    }
}