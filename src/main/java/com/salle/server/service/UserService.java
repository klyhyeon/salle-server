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
    public User saveUser(User user) {
        String rawPwd = user.getPassword();
        user.encryptAndSetPassword(rawPwd);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void verifyLogin(User user) {
        String logInUserEmail = user.getEmail();
        String rawUserInputPwd = user.getPassword();
        User logInUserInfoFromDB = userRepository.findByEmail(logInUserEmail);

        checkEmailAndPwd(logInUserInfoFromDB, rawUserInputPwd);
    }

    public void checkEmailAndPwd(User logInUserInfoFromDB, String rawUserInputPwd) {
        if (logInUserInfoFromDB == null) {
            throw new SlErrorException(ErrorCode.USER_NOT_FOUND);
        }

        if (logInUserInfoFromDB.isWrongPassword(rawUserInputPwd)) {
            throw new SlErrorException(ErrorCode.WRONG_PASSWORD);
        }
    }
}
