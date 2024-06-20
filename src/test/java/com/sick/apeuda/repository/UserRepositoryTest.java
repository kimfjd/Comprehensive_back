package com.sick.apeuda.repository;


import com.sick.apeuda.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입")
    public void testUserSignup() {
        // Given
        User user = new User();
        user.setEmail("test4@example.com");
        user.setName("Test User4");
        user.setPassword("password3");
        user.setIdentityNumber("1234561");

        // When
        userRepository.save(user);
    }

    @Test
    @DisplayName("로그인")
    public void testLogin() {
        Optional<User> login = userRepository.findByEmailAndPassword("test1@example.com", "password2");
        if(login.isPresent()) {
            System.out.println("로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }
    }


}
