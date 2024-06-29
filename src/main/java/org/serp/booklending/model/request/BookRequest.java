package org.serp.booklending.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        Long id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String authorName,
        @NotNull(message = "1012")
        @NotEmpty(message = "102")
        String isbn,
        @NotNull(message = "1013")
        @NotEmpty(message = "103")
        String summary,

        boolean shareable) {
}
