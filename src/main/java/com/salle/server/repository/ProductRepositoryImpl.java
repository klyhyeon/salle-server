package com.salle.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.QProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Product> findByTitleOrDescription(String searchWord, String searchWordNoSpace) {
        QProduct qProduct = QProduct.product;
        return jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.title.contains(searchWord)
                        .or(qProduct.title.trim().contains(searchWordNoSpace)))
                .fetch();
    }
}
