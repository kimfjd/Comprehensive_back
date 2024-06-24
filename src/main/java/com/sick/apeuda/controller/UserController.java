//UserController.java
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
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .identityNumber(userDto.getIdentityNumber())
                .nickname(userDto.getNickname())
                .profileImgPath(userDto.getProfileImgPath())
                .skill(userDto.getSkill())
                .myInfo(userDto.getMyInfo())
                .build();

        boolean isTrue = userService.saveUser(user);

        return ResponseEntity.ok(isTrue);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody UserDto userDto) {
        boolean isTrue = userService.login(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(isTrue);
    }


    // 회원정보 가져오기
    @GetMapping("/userinfo")
    public ResponseEntity<UserDto> userInfo(@RequestParam String email) {
        UserDto userDto = userService.getUserInfo(email);
        return ResponseEntity.ok(userDto);
    }

    // 회원 수정
    @PutMapping("/usermodify/{email}")
    public ResponseEntity<Boolean> userModify(@RequestBody UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .identityNumber(userDto.getIdentityNumber())
                .nickname(userDto.getNickname())
                .profileImgPath(userDto.getProfileImgPath())
                .skill(userDto.getSkill())
                .myInfo(userDto.getMyInfo())
                .build();
        boolean isTrue = userService.saveUser(user);
        return ResponseEntity.ok(isTrue);
    }

    // 회원 삭제
    @DeleteMapping("/deluser/{email}")
    public ResponseEntity<Boolean> userDelete(@PathVariable String email) {
        boolean isTrue = userService.deleteUser(email);
        return ResponseEntity.ok(isTrue);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> userList(){
        List<UserDto> list =userService.getUserList();
        return ResponseEntity.ok(list);

    }

}
