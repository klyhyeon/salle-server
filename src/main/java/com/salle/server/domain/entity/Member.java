package com.salle.server.domain.entity;

import com.salle.server.utils.Encrypt;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id; //자동생성
    private String phone;
    @Column(name = "member_name")
    private String name;
    private String email;
    private String nickName;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;
    private int status; //휴면, 탈퇴, 활동

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Product> products;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<MemberChatRoom> memberChatRooms;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWrongPassword(String rawMemberInputPwd) {
        String encryptedMemberInputPwd = Encrypt.createPassword(rawMemberInputPwd);
        return !password.equals(encryptedMemberInputPwd);
    }

    public void encryptAndSetPassword(String rawPwd) {
        this.password = Encrypt.createPassword(rawPwd);
    }
}
