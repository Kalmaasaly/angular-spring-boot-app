package org.serp.booklending.repository;

import org.serp.booklending.model.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long>,
        JpaSpecificationExecutor<BookTransactionHistory> {
    @Query("""
            select history from BookTransactionHistory history
            where history.user.id=:userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Long userId);

    @Query("""
            select history from BookTransactionHistory history
            where history.book.owner.id=:userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, long userId);

    @Query("""
            select  (count(*)>0) AS isBorrowed  from BookTransactionHistory history
            where history.user.id=:userId
            and history.book.id=:bookId
            and history.returnedApproved=false
            """)
    boolean isAlreadyBorrowedByUserId(Long bookId, Long userId);

    @Query("""
            select history from BookTransactionHistory history
            where history.user.id=:userId
            and history.book.id=:bookId
            and history.returned= false
            and history.returnedApproved=false
            """)
    Optional<BookTransactionHistory> findBookIdAndUserId(Long bookId, Long userId);

    @Query("""
            select history from BookTransactionHistory history
            where history.book.owner.id=:userId
            and history.book.id=:bookId
            and history.returned= true
            and history.returnedApproved=false
            """)
    Optional<BookTransactionHistory> findBookIdAndOwnerId(Long bookId, Long userId);
}
