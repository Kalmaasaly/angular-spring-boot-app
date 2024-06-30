package org.serp.booklending.repository.queries;

import org.serp.booklending.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookQuery {

    public static Specification<Book> withOwnerId(Long ownerId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"),ownerId));
    }
}
