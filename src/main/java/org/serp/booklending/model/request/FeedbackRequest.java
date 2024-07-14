package org.serp.booklending.model.request;

import jakarta.validation.constraints.*;

public record FeedbackRequest(
        @Positive(message = "200")
        @Min(value=0,message = "201")
        @Max(value=5,message = "202")
        Double rate,
        @NotNull(message = "200")
        @NotEmpty(message = "2001")
        @NotBlank(message="2003")
        String comment,
        @NotNull(message = "123")
        Long bookId) {
}
