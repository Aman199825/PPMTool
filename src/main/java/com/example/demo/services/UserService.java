package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.exceptions.UserNameAlreadyExistsException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public User saveUser(User newUser){
        try {
            String pass = bCryptPasswordEncoder.encode(newUser.getPassword());
            newUser.setPassword(pass);
            newUser = userRepository.save(newUser);
            return newUser;

        }
        catch (Exception ex){
            throw new UserNameAlreadyExistsException("username already taken");
        }
    }
}
