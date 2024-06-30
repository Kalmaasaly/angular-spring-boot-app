package org.serp.booklending.repository;

import org.serp.booklending.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistoryRepository,Long> ,
        JpaSpecificationExecutor<org.serp.booklending.model.BookTransactionHistory> {
    Page<Book> findAllBorrowedBooks(long id);
}
