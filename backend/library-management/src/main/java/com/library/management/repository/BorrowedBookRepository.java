
package com.library.management.repository;

import com.library.management.entity.BorrowedBook;
import com.library.management.entity.User;
import com.library.management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    List<BorrowedBook> findByUserAndIsReturned(User user, boolean isReturned);
    List<BorrowedBook> findByBookAndIsReturned(Book book, boolean isReturned);
    Optional<BorrowedBook> findByUserAndBookAndIsReturned(User user, Book book, boolean isReturned);
    List<BorrowedBook> findByIsReturned(boolean isReturned);

//    @Modifying
//    @Query("UPDATE BorrowedBook bb SET bb.deleted = true WHERE bb.book.id = :bookId")
//    int softDeleteByBookId(@Param("bookId") Long bookId);

    long deleteByBook(Book book);

}