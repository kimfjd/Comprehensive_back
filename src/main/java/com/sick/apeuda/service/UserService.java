package com.sick.apeuda.service;

import com.sick.apeuda.dto.UserDto;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean signUp(User user) {
        User rst = userRepository.save(user);
        return rst != null;
    }

    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.isPresent();
    }

    public List<UserDto> getUserList(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users) {
            userDtos.add(UserDto.of(user));
        }
        return userDtos;
    }
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> user = userRepository.findById(email);
        return user.map(UserDto::of);
    }

}
