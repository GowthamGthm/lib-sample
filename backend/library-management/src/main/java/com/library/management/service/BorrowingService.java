
package com.library.management.service;

import com.library.management.dto.BorrowedBookDto;
import com.library.management.entity.Book;
import com.library.management.entity.BorrowedBook;
import com.library.management.entity.User;
import com.library.management.repository.BorrowedBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {
    
    @Autowired
    BorrowedBookRepository borrowedBookRepository;
    
    @Autowired
    BookService bookService;
    
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    
    public BorrowedBookDto borrowBook(Long userId, Long bookId) {
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
        BorrowedBookDto borrowedBookDto = modelMapper.map(borrowedBook, BorrowedBookDto.class);
        return borrowedBookDto;
    }
    
    public BorrowedBookDto returnBook(Long userId, Long bookId) {
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
        BorrowedBookDto borrowedBookDto = modelMapper.map(borrowedBook, BorrowedBookDto.class);
        return borrowedBookDto;
    }
    
    public List<BorrowedBookDto> getUserBorrowedBooks(Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<BorrowedBook> userNotReturnedBooks = borrowedBookRepository.findByUserAndIsReturned(user, false);

        List<BorrowedBookDto> userBooksList = Optional.ofNullable(userNotReturnedBooks).orElse(Collections.emptyList())
                .stream()
                .map(ele -> modelMapper.map(ele, BorrowedBookDto.class))
                .toList();

        return userBooksList;
    }
    
    public List<BorrowedBookDto> getAllBorrowedBooks() {

        List<BorrowedBook> allBooked = borrowedBookRepository.findByIsReturned(false);

        List<BorrowedBookDto> allBorrowedList = Optional.ofNullable(allBooked).orElse(Collections.emptyList())
                .stream()
                .map(ele -> modelMapper.map(ele, BorrowedBookDto.class))
                .toList();

        return allBorrowedList;

    }
    
    public List<BorrowedBookDto> getAllBorrowingHistory() {

        List<BorrowedBook> borrowingHistory = borrowedBookRepository.findAll();

        List<BorrowedBookDto> allBorrowedList = Optional.ofNullable(borrowingHistory).orElse(Collections.emptyList())
                .stream()
                .map(ele -> modelMapper.map(ele, BorrowedBookDto.class))
                .toList();

        return allBorrowedList;

    }

}