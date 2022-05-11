package com.salle.server.service;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.ErrorCode;
import com.salle.server.error.SlErrorException;
import com.salle.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveMember(User member) {
        String rawPwd = member.getPassword();
        member.encryptAndSetPassword(rawPwd);
        return userRepository.save(member);
    }

    @Transactional(readOnly = true)
    public User findMemberByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void verifyLogin(User member) {
        String logInMemberEmail = member.getEmail();
        String rawMemberInputPwd = member.getPassword();
        User logInMemberInfoFromDB = userRepository.findByEmail(logInMemberEmail);

        checkEmailAndPwd(logInMemberInfoFromDB, rawMemberInputPwd);
    }

    public void checkEmailAndPwd(User logInMemberInfoFromDB, String rawMemberInputPwd) {
        if (logInMemberInfoFromDB == null) {
            throw new SlErrorException(ErrorCode.USER_NOT_FOUND);
        }

        if (logInMemberInfoFromDB.isWrongPassword(rawMemberInputPwd)) {
            throw new SlErrorException(ErrorCode.WRONG_PASSWORD);
        }
    }
}
