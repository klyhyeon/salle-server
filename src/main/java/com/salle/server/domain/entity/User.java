package com.salle.server.domain.entity;

import com.salle.server.domain.enumeration.OauthType;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    public User() {
    }

    @Builder
    public User(String oauthLogin, String oauthId, OauthType oauthType) {
        this.oauthLogin = oauthLogin;
        this.oauthId = oauthId;
        this.oauthType = oauthType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //자동생성
    private String name;
    private String oauthLogin;
    private String oauthId;
    @Enumerated(value = EnumType.STRING)
    private OauthType oauthType;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;

    @OneToMany(mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ProductComment> productComments = new ArrayList<>();

    public String getOauthId() {
        return oauthId;
    }

    public OauthType getOauthType() {
        return oauthType;
    }

    public Long getId() {
        return id;
    }

    public String getOauthLogin() {
        return oauthLogin;
    }

}
