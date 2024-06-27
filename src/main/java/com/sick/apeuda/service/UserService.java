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

    // 회원 가입 여부 확인
    public boolean isUser(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원 정보 저장(회원 가입, 회원 수정)
    public boolean saveUser(User user) {
        User rst = userRepository.save(user);
        return rst != null;
    }

    // 로그인
    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.isPresent();
    }

    // 회원 정보 조회(로그인 한 사용자)
    public UserDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );
        return UserDto.of(user);
    }

    // 회원 삭제
    public boolean deleteUser(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            userRepository.delete(user);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }


    // 회원 정보 전체 조회
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
