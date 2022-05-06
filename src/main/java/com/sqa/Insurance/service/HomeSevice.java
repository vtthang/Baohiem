package com.sqa.Insurance.service;

import com.sqa.Insurance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sqa.Insurance.repository.UserRepository;
@Service
public class HomeSevice {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
