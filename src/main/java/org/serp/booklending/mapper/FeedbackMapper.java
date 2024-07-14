package org.serp.booklending.mapper;

import org.serp.booklending.model.Book;
import org.serp.booklending.model.Feedback;
import org.serp.booklending.model.request.FeedbackRequest;
import org.serp.booklending.model.response.FeedbackResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request) {
            return Feedback.builder()
                    .rate(request.rate())
                    .comment(request.comment())
                    .book(Book.builder().id(request.bookId()).build())
                    .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Long userId) {
        return  FeedbackResponse.builder()
                .rate(feedback.getRate())
                .comment(feedback.getComment())
                .ownerFeedback(Objects.equals(feedback.getCreatedBy(),userId))
                .build();

    }
}
