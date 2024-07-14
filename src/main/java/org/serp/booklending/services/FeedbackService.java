package org.serp.booklending.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.handler.OperationNotPermittedException;
import org.serp.booklending.mapper.FeedbackMapper;
import org.serp.booklending.model.BookTransactionHistory;
import org.serp.booklending.model.Feedback;
import org.serp.booklending.model.User;
import org.serp.booklending.model.request.FeedbackRequest;
import org.serp.booklending.model.response.BorrowedBookResponse;
import org.serp.booklending.model.response.FeedbackResponse;
import org.serp.booklending.model.response.PageResponse;
import org.serp.booklending.repository.BookRepository;
import org.serp.booklending.repository.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private BookRepository bookRepository;
    private FeedbackRepository feedbackRepository;
    private FeedbackMapper feedbackMapper;
    public Long save(FeedbackRequest request, Authentication authentication) {
        var book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No Book with this ID" + request.bookId()));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPermittedException("You cannot gave feedback for this book");
        }
        User user = (User) authentication.getPrincipal();
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give feedback for your book!");
        }
        var feedback=feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByBookId(Long bookId, int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Feedback> feedbacks = feedbackRepository.findAllFeedbacks(pageable, bookId);
        List<FeedbackResponse> feedbackResponseList = feedbacks.stream()
                .map(f-> feedbackMapper.toFeedbackResponse(f,user.getId()))
                .toList();
        return new PageResponse<>(feedbackResponseList,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast());
    }
}
