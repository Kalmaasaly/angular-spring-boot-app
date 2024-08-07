package org.serp.booklending.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.model.request.FeedbackRequest;
import org.serp.booklending.model.response.FeedbackResponse;
import org.serp.booklending.model.response.PageResponse;
import org.serp.booklending.services.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedBackController {
    private final FeedbackService feedbackService;

    @PostMapping("/saveFeedback")
    @Operation(summary = "Insert a new saveFeedback")
    public ResponseEntity<Long> saveFeedback(@Valid @RequestBody FeedbackRequest  requestFeedback, Authentication authentication) {
        return ResponseEntity.ok(feedbackService.save(requestFeedback, authentication));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findFeedbacks(
            @PathVariable("book-id") Long bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication){


        return ResponseEntity.ok(feedbackService.findAllFeedbacksByBookId(bookId,page, size, authentication));
    }
}
