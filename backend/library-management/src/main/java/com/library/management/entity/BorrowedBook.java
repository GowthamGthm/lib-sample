
package com.library.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrowed_books")
@Getter
@Setter
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;
    
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;
    
    @Column(name = "is_returned", nullable = false, columnDefinition = "boolean default false")
    private boolean isReturned;


    public BorrowedBook() {
        this.borrowedAt = LocalDateTime.now();
        this.isReturned = false;
    }
    
    public BorrowedBook(User user, Book book) {
        this();
        this.user = user;
        this.book = book;
    }
    

}
