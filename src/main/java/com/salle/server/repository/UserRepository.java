package com.salle.server.repository;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.OauthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthTypeAndOauthId(OauthType oauthType, String oauthId);
}
