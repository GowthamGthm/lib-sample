
package com.library.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrowed_books")
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
    
    @Column(name = "is_returned")
    private Boolean isReturned;
    
    public BorrowedBook() {
        this.borrowedAt = LocalDateTime.now();
        this.isReturned = false;
    }
    
    public BorrowedBook(User user, Book book) {
        this();
        this.user = user;
        this.book = book;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public void setBorrowedAt(LocalDateTime borrowedAt) { this.borrowedAt = borrowedAt; }
    
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
    
    public Boolean getIsReturned() { return isReturned; }
    public void setIsReturned(Boolean isReturned) { this.isReturned = isReturned; }
}
