package com.salle.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salle.server.config.QueryDSLConfig;
import com.salle.server.domain.entity.Product;
import com.salle.server.domain.entity.QProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDSLConfig.class)
@ActiveProfiles("dev")
class ProductRepositoryImplTest {


    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    @DisplayName("QueryDSL read 테스트")
    void queryDslCrudTest() {
        QProduct qProduct = QProduct.product;
        List<Product> allProducts = jpaQueryFactory.selectFrom(qProduct).fetch();
        assertThat(allProducts.size()).isEqualTo(0);
    }
}