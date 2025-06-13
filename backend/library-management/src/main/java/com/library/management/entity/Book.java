
package com.library.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(unique = true, nullable = false)
    private String isbn;
    
    private String genre;
    
    @Column(name = "publication_year")
    private Integer publicationYear;
    
    @Column(name = "total_copies")
    private Integer totalCopies;
    
    @Column(name = "available_copies")
    private Integer availableCopies;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public Book() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Book(String title, String author, String isbn, String genre, Integer publicationYear, Integer totalCopies) {
        this();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    
    public Integer getTotalCopies() { return totalCopies; }
    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
    
    public Integer getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
