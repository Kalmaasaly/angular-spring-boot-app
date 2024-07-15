package org.serp.booklending.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.model.request.BookRequest;
import org.serp.booklending.model.response.BookResponse;
import org.serp.booklending.model.response.BorrowedBookResponse;
import org.serp.booklending.model.response.PageResponse;
import org.serp.booklending.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(name = "books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private BookService bookService;

    @PostMapping
    public ResponseEntity<Long> saveBook(@Valid @RequestBody BookRequest requestBook, Authentication authentication) {
        return ResponseEntity.ok(bookService.save(requestBook, authentication));
    }

    @GetMapping("{book-id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("book-id") Long bookId) {
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllBook(page, size, authentication));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, authentication));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, authentication));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, authentication));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Long> updateShareableBook(@PathVariable("book-id") Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.updateShareableBook(bookId, authentication));
    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Long> updateArchivedBook(@PathVariable("book-id") Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.updateArchivedBook(bookId, authentication));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Long> borrowBook(@PathVariable("book-id") Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.borrowBook(bookId, authentication));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Long> returnBorrowBook(@PathVariable("book-id") Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.returnBorrowBook(bookId, authentication));
    }

    @PatchMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<Long> returnApproveBook(@PathVariable("book-id") Long bookId, Authentication authentication) {
        return ResponseEntity.ok(bookService.returnApproveBook(bookId, authentication));
    }
    @PostMapping(value = "/cover/{book-id}",consumes = "multipart/form")
    public ResponseEntity<?> uploadCoverImage(@PathVariable("book-id") Long bookId,
                                              @Parameter()
                                              @RequestPart("file") MultipartFile file,
                                              Authentication authentication){

        bookService.uploadBookCoverImage(bookId,file,authentication);
        return  ResponseEntity.accepted().build();

    }
}
