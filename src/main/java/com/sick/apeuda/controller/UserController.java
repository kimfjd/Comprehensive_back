package com.sick.apeuda.controller;


import com.sick.apeuda.dto.UserDto;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> userSignup (@RequestBody UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setIdentityNumber(userDto.getIdentityNumber());
        user.setNickname(userDto.getNickname());
        user.setProfileImgPath(userDto.getProfileImgPath());
        user.setSkill(userDto.getSkill());
        user.setMyInfo(userDto.getMyInfo());
        boolean isTrue = userService.signUp(user);

        return ResponseEntity.ok(isTrue);
    }


}
