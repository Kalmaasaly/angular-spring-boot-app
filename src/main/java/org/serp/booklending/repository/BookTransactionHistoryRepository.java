package org.serp.booklending.repository;

import org.serp.booklending.model.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistoryRepository,Long> ,
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
}
