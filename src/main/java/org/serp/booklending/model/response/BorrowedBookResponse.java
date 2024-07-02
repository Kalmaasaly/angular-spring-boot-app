package org.serp.booklending.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
public record BorrowedBookResponse (Long id,
     String title,
     String authorName,
     String isbn,
     double rate,
     boolean returned,
     boolean returnedApproved){}
