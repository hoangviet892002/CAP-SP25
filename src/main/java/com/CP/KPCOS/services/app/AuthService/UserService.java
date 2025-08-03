package com.CP.KPCOS.services.app.AuthService;

import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.exceptions.NotFoundException;
import com.CP.KPCOS.repository.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}
