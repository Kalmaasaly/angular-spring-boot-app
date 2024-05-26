package org.serp.booklending.repository;

import org.serp.booklending.model.Role;
import org.serp.booklending.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);
}
