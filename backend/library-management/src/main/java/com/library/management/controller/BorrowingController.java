
package com.library.management.controller;

import com.library.management.dto.BorrowedBookDto;
import com.library.management.entity.BorrowedBook;
import com.library.management.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/private/api/borrowing")
public class BorrowingController {
    
    @Autowired
    private BorrowingService borrowingService;
    
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long bookId = request.get("bookId");
            BorrowedBookDto borrowedBook = borrowingService.borrowBook(userId, bookId);
            return ResponseEntity.ok(borrowedBook);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Borrowing failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long bookId = request.get("bookId");
            BorrowedBookDto borrowedBook = borrowingService.returnBook(userId, bookId);
            return ResponseEntity.ok(borrowedBook);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Return failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowedBookDto>> getUserBorrowedBooks(@PathVariable Long userId) {
        try {
            List<BorrowedBookDto> borrowedBooks = borrowingService.getUserBorrowedBooks(userId);
            return ResponseEntity.ok(borrowedBooks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<BorrowedBookDto>> getAllBorrowedBooks() {
        try {
            List<BorrowedBookDto> borrowedBooks = borrowingService.getAllBorrowedBooks();
            return ResponseEntity.ok(borrowedBooks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<BorrowedBookDto>> getAllBorrowingHistory() {
        try {
            List<BorrowedBookDto> borrowingHistory = borrowingService.getAllBorrowingHistory();
            return ResponseEntity.ok(borrowingHistory);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
