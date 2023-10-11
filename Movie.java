class Movie {
    private String title;
    private String rating;
    private String genre;

    public Movie(String title, String rating, String genre) {
        this.title = title;
        this.rating = rating;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }
}