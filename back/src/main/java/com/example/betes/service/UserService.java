package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.User;
import com.example.betes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent() ) { return optUser.get();} else {throw new ResourceNotFoundException();
        }
    }


}
