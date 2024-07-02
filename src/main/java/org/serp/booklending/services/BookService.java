package org.serp.booklending.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.handler.OperationNotPermittedException;
import org.serp.booklending.mapper.BookMapper;
import org.serp.booklending.model.Book;
import org.serp.booklending.model.BookTransactionHistory;
import org.serp.booklending.model.User;
import org.serp.booklending.model.request.BookRequest;
import org.serp.booklending.model.response.BookResponse;
import org.serp.booklending.model.response.BorrowedBookResponse;
import org.serp.booklending.model.response.PageResponse;
import org.serp.booklending.repository.BookRepository;
import org.serp.booklending.repository.BookTransactionHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.serp.booklending.repository.queries.BookQuery.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository bookHistoryRepository;

    private final BookMapper bookMapper;

    public Long save(BookRequest bookRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Book book = bookMapper.toBook(bookRequest);
        book.setArchived(false);
        book.setOwner(user);
        return bookRepository.save(book).getId();

    }

    public BookResponse findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No Book with this ID" + bookId));
    }

    public PageResponse<BookResponse> findAllBook(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponseList = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(bookResponseList,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponseList = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(bookResponseList,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponseList = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(bookResponseList,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> returnedBooks = bookHistoryRepository.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponseList = returnedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(bookResponseList,
                returnedBooks.getNumber(),
                returnedBooks.getSize(),
                returnedBooks.getTotalElements(),
                returnedBooks.getTotalPages(),
                returnedBooks.isFirst(),
                returnedBooks.isLast());
    }

    public Long updateShareableBook(Long bookId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book with this ID" + bookId));
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot update shareable status of book");
        }
        book.setShareable(true);
        book.setLastModifiedBy(user.getId());
        book.setLastModifiedDate(LocalDateTime.now());

        return bookId;
    }

    public Long updateArchivedBook(Long bookId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No Book with this ID" + bookId));
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot update shareable status of book");
        }
        book.setArchived(true);
        book.setLastModifiedBy(user.getId());
        book.setLastModifiedDate(LocalDateTime.now());

        return bookId;
    }
}

