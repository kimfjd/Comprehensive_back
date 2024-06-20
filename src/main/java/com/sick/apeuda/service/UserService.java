package com.sick.apeuda.service;

import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean signUp(User user) {
        User rst = userRepository.save(user);
        return rst != null;
    }
}
