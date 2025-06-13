
package com.library.management.controller;

import com.library.management.entity.Book;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/private/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            return bookService.getBookById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        try {
            List<Book> books = bookService.getAvailableBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        try {
            List<Book> books = bookService.searchBooksByTitle(title);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search/author")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@RequestParam String author) {
        try {
            List<Book> books = bookService.searchBooksByAuthor(author);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            Book newBook = bookService.createBook(book);
            return ResponseEntity.ok(newBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Book creation failed: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Book update failed: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Book deletion failed: " + e.getMessage());
        }
    }
}
