package org.serp.booklending.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.model.Book;
import org.serp.booklending.model.request.BookRequest;
import org.serp.booklending.model.response.BookResponse;
import org.serp.booklending.model.response.PageResponse;
import org.serp.booklending.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private BookService bookService;

    @PostMapping
    public ResponseEntity<Long> saveBook(@Valid @RequestBody BookRequest requestBook, Authentication authentication){
        return ResponseEntity.ok(bookService.save(requestBook, authentication));
    }
    @GetMapping("{book-id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("book-id") Long bookId){
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }
    @GetMapping("{book-id}")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name="page",defaultValue = "0",required = false) int page,
            @RequestParam(name="size",defaultValue = "10",required = false) int size,
            Authentication authentication){
        return ResponseEntity.ok(bookService.findAllBook(page,size,authentication));
    }
}
