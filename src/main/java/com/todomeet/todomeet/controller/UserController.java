package com.todomeet.todomeet.controller;

import com.todomeet.todomeet.dto.JwtAuthDto;
import com.todomeet.todomeet.dto.UserDto;
import com.todomeet.todomeet.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    public final UserService userService;

    @PostMapping("/auth/login")
    public JwtAuthDto login(@RequestBody Map<String, String> requestBody) throws Exception{
        log.info("로그인 start");
        String userEmail = requestBody.get("userEmail");
        JwtAuthDto jwtAuthDto = userService.login(userEmail);
        log.info("로그인 end");
        return jwtAuthDto;
    }


    @PostMapping("/auth/sign-up")
    public JwtAuthDto signUp(@RequestBody UserDto userDto) throws Exception {
        log.info("회원가입 start");
        JwtAuthDto jwtAuthDto = userService.register(userDto);
        log.info("회원가입 end");
        return jwtAuthDto;
    }

    @DeleteMapping("")
    public void deleteUser(@RequestParam String userEmail) throws Exception{
        log.info("탈외 start");
        userService.delete(userEmail);
        log.info("탈퇴 end");

    }
}
