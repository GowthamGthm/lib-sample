
package com.library.management.repository;

import com.library.management.entity.BorrowedBook;
import com.library.management.entity.User;
import com.library.management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUserAndIsReturned(User user, Boolean isReturned);
    List<BorrowedBook> findByBookAndIsReturned(Book book, Boolean isReturned);
    Optional<BorrowedBook> findByUserAndBookAndIsReturned(User user, Book book, Boolean isReturned);
    List<BorrowedBook> findByIsReturned(Boolean isReturned);
}
