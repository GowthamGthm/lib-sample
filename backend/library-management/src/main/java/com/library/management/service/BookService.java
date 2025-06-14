
package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.BorrowedBook;
import com.library.management.exceptions.BookOperationFailed;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    public Book createBook(Book book) {
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookOperationFailed(HttpStatus.NOT_FOUND , "Book not found" , "Book not found"));
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setGenre(bookDetails.getGenre());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setTotalCopies(bookDetails.getTotalCopies());
        
        // Adjust available copies if total copies changed
        int difference = bookDetails.getTotalCopies() - book.getTotalCopies();
        book.setAvailableCopies(book.getAvailableCopies() + difference);
        
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        List<BorrowedBook> bookNotReturned = borrowedBookRepository.findByBookAndIsReturned(new Book(id), false);

        if(!CollectionUtils.isEmpty(bookNotReturned)) {
            throw new BookOperationFailed(HttpStatus.BAD_REQUEST, "Delete blocked: outstanding books not returned", "Delete blocked: outstanding books not returned");
        }

        borrowedBookRepository.deleteByBook(new Book(id));
        bookRepository.deleteById(id);
    }
    
    public boolean borrowBook(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent() && bookOpt.get().getAvailableCopies() > 0) {
            Book book = bookOpt.get();
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
            return true;
        }
        return false;
    }
    
    public boolean returnBook(Long bookId) {

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getAvailableCopies() < book.getTotalCopies()) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }
}
