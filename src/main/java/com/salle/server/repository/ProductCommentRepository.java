package com.salle.server.repository;

import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {


    Page<ProductComment> findAllByProduct(Pageable pageable, Product product);
}
