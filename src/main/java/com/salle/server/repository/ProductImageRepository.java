package com.salle.server.repository;

import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<String> findByProduct(Product product);
}
