package org.serp.booklending.mapper;


import org.serp.booklending.model.Book;
import org.serp.booklending.model.BookTransactionHistory;
import org.serp.booklending.model.request.BookRequest;
import org.serp.booklending.model.response.BookResponse;
import org.serp.booklending.model.response.BorrowedBookResponse;
import org.serp.booklending.utils.FileUtils;


public class BookMapper {

    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .isbn(request.isbn())
                .authorName(request.authorName())
                .summary(request.summary())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .summary(book.getSummary())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(book.getOwner().getFullName())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }


    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        var book=history.getBook();
        return BorrowedBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .rate(book.getRate())
                .returned(history.isReturned())
                .returnedApproved(history.isReturnedApproved())
                .build();
    }
}
