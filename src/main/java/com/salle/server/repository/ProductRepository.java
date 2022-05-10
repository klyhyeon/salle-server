package com.salle.server.repository;

import com.salle.server.domain.entity.Category;
import com.salle.server.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(Category category);
}
