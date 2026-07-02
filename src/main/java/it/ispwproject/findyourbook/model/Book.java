package it.ispwproject.findyourbook.model;

public class Book {
    private int id;
    private String titolo;
    private String autore;
    private String genere;
    private String immagineUrl;

    // COSTRUTTORE PUBBLICO (fondamentale)
    public Book(int id, String titolo, String autore, String genere, String immagineUrl) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.immagineUrl = immagineUrl;
    }

    // GETTER (obbligatori)
    public int getId() { return id; }
    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public String getGenere() { return genere; }
    public String getImmagineUrl() { return immagineUrl; }
}