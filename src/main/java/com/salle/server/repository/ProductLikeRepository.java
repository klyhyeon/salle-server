package com.salle.server.repository;

import com.salle.server.domain.entity.ProductLike;
import com.salle.server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    List<ProductLike> findAllByUser(User user);
}
