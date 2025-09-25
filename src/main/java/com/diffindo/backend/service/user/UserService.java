package com.diffindo.backend.service.user;

import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String name, String phoneNumber, String email, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(name, phoneNumber, email, hashedPassword);
        return userRepository.save(newUser);
    }
}
