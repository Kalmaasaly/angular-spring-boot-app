package org.serp.booklending.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.mapper.BookMapper;
import org.serp.booklending.model.Book;
import org.serp.booklending.model.User;
import org.serp.booklending.model.request.BookRequest;
import org.serp.booklending.model.response.BookResponse;
import org.serp.booklending.model.response.PageResponse;
import org.serp.booklending.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public Long save(BookRequest bookRequest, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        Book book=bookMapper.toBook(bookRequest);
        book.setArchived(false);
        book.setOwner(user);
        return bookRepository.save(book).getId();

    }
    public BookResponse findBookById(Long bookId){
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(()-> new EntityNotFoundException("No Book with this ID"+bookId));
    }

    public PageResponse<BookResponse> findAllBook(int page, int size, Authentication authentication) {
        User user= (User) authentication.getPrincipal();
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books=bookRepository.findAllDisplayableBooks(pageable,user.getId());
        List<BookResponse> bookResponseList=books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return  new PageResponse<>(bookResponseList,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }
}

