
package com.library.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
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

    public Book(Long id) {
        this.id = id;
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

}
