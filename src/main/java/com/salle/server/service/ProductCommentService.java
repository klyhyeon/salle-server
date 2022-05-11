package com.salle.server.service;

import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.ProductComment;
import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.ErrorCode;
import com.salle.server.error.SlErrorException;
import com.salle.server.repository.ProductCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommentService {

    private ProductCommentRepository productCommentRepository;

    public ProductCommentService(ProductCommentRepository productCommentRepository) {
        this.productCommentRepository = productCommentRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductComment> getAllByProduct(Pageable pageable, Product product) {
        return productCommentRepository.findAllByProduct(pageable, product);
    }

    @Transactional
    public void save(Product product, User user) {
        ProductComment productComment = ProductComment.getRawInstance(product, user);
        productCommentRepository.save(productComment);
    }

    @Transactional
    public void saveNestedComment(Product product, User user, ProductComment nestedProductComment) {
        ProductComment productComment = ProductComment.getNestedInstance(product, user, nestedProductComment);
        productCommentRepository.save(productComment);
    }

    @Transactional(readOnly = true)
    public ProductComment getById(Long id) {
        return productCommentRepository.findById(id)
                .orElseThrow(() -> new SlErrorException(ErrorCode.PRODUCT_COMMENT_NOT_FOUND));
    }

    @Transactional
    public void delete(Long productCommentId) {
        ProductComment productComment = getById(productCommentId);
        productComment.setUpdatedAndDeletedTime();
    }

    @Transactional
    public ProductComment update(ProductComment requestProductComment, ProductComment currentProductComment) {
        currentProductComment.updateProductComment(requestProductComment);
        return productCommentRepository.save(currentProductComment);
    }
}
