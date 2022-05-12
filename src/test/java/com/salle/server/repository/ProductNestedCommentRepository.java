package com.salle.server.repository;

import com.salle.server.domain.entity.ProductNestedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNestedCommentRepository extends JpaRepository<ProductNestedComment, Long> {
}
