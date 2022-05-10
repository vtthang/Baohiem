package com.sqa.Insurance.service;

import com.sqa.Insurance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sqa.Insurance.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeSevice {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        if (userRepository.findByUsername(username) != null) {
            return userRepository.findByUsername(username);
        }
        return null;
    }

    public User findUserById(long id) {
        if(userRepository.findById(id) != null) {
          return  userRepository.findById(id);
        }
        return null;
    }

    public List<User> getAllUserExceptAdmin() {
        return userRepository.findUsers();
    }

    public User getUserByUsernameAndCCCD(String username, String cccd) {
       if (userRepository.findUser(username, cccd) != null) {
           return userRepository.findUser(username, cccd);
       }
       return null;
    }

    public User saveUser(User user) {
      return userRepository.save(user);
    }




}
