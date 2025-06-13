
package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.BorrowedBook;
import com.library.management.entity.User;
import com.library.management.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {
    
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    public BorrowedBook borrowBook(Long userId, Long bookId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookService.getBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book is not available");
        }
        
        // Check if user already borrowed this book
        Optional<BorrowedBook> existingBorrow = borrowedBookRepository
                .findByUserAndBookAndIsReturned(user, book, false);
        if (existingBorrow.isPresent()) {
            throw new RuntimeException("User has already borrowed this book");
        }
        
        // Create borrowing record
        BorrowedBook borrowedBook = new BorrowedBook(user, book);
        borrowedBook = borrowedBookRepository.save(borrowedBook);
        
        // Update book availability
        bookService.borrowBook(bookId);
        
        return borrowedBook;
    }
    
    public BorrowedBook returnBook(Long userId, Long bookId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookService.getBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        BorrowedBook borrowedBook = borrowedBookRepository
                .findByUserAndBookAndIsReturned(user, book, false)
                .orElseThrow(() -> new RuntimeException("No active borrowing record found"));
        
        // Mark as returned
        borrowedBook.setIsReturned(true);
        borrowedBook.setReturnedAt(LocalDateTime.now());
        borrowedBook = borrowedBookRepository.save(borrowedBook);
        
        // Update book availability
        bookService.returnBook(bookId);
        
        return borrowedBook;
    }
    
    public List<BorrowedBook> getUserBorrowedBooks(Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return borrowedBookRepository.findByUserAndIsReturned(user, false);
    }
    
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepository.findByIsReturned(false);
    }
    
    public List<BorrowedBook> getAllBorrowingHistory() {
        return borrowedBookRepository.findAll();
    }
}
