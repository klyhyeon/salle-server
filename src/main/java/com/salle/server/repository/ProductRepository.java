package com.salle.server.repository;

import com.salle.server.domain.entity.Category;
import com.salle.server.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory(Pageable pageable, Category category);

    Page<Product> findAllByDeletedTimeIsNull(Pageable pageable);

}
