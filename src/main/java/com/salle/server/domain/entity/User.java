package com.salle.server.domain.entity;

import com.salle.server.utils.Encrypt;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //자동생성
    private String phone;
    private String name;
    private String email;
    private String nickName;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWrongPassword(String rawUserInputPwd) {
        String encryptedUserInputPwd = Encrypt.createPassword(rawUserInputPwd);
        return !password.equals(encryptedUserInputPwd);
    }

    public void encryptAndSetPassword(String rawPwd) {
        this.password = Encrypt.createPassword(rawPwd);
    }
}
