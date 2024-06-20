package com.sick.apeuda.controller;


import com.sick.apeuda.dto.UserDto;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // 회원가입(기본적인 것만, 아이디,비번,이름,주민번호는 필수 입력)
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

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody UserDto userDto) {
        boolean isTrue = userService.login(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(isTrue);
    }


    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> userList(){
        List<UserDto> list =userService.getUserList();
        return ResponseEntity.ok(list);

    }

}
