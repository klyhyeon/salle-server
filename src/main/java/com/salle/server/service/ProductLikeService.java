package com.salle.server.service;

import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.ProductLike;
import com.salle.server.domain.entity.User;
import com.salle.server.repository.ProductLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductLikeService {

    private ProductLikeRepository productLikeRepository;

    public ProductLikeService(ProductLikeRepository productLikeRepository) {
        this.productLikeRepository = productLikeRepository;
    }

    @Transactional
    public void likeProduct(Product product, User user) {
        ProductLike productComment = ProductLike.getInstance(product, user);
        productLikeRepository.save(productComment);
    }
}
