package it.ispwproject.findyourbook.bean;

public class BookBean {
    private String title;
    private String author;
    private String genre;
    private String imageUrl;
    private int rating;
    private String description; // <--- NUOVO CAMPO!
    private String status;

    // Costruttore "completo" per le nuove ricerche
    public BookBean(String title, String author, String genre, String imageUrl, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Costruttore "vecchio" (per non far arrabbiare il DAO del database)
    public BookBean(String title, String author, String genre, String imageUrl) {
        this(title, author, genre, imageUrl, "Trama non disponibile.");
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getImageUrl() { return imageUrl; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}