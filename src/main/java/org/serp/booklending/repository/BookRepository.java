package org.serp.booklending.repository;

import org.serp.booklending.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT book from Book book where book.archived=false and book.shareable=true and book.owner !=:userId")
    Page<Book> findAllDisplayableBooks(Pageable pageable, long userId);

    /*@Query("SELECT book from Book book where  book.owner=userId")
    Page<Book> findAllDisplayableBooksByOwner(Pageable pageable, long userId);*/
}
