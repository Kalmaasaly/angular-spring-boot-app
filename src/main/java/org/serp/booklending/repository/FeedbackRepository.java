package org.serp.booklending.repository;

import org.serp.booklending.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    @Query("""
            select feedback from Feedback feedback 
            where feedback.book.id=:bookId
            """)
    Page<Feedback> findAllFeedbacks(Pageable pageable, Long bookId);
}
