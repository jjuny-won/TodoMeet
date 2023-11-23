package com.todomeet.todomeet.controller;

import com.todomeet.todomeet.dto.JwtAuthDto;
import com.todomeet.todomeet.dto.UserDto;
import com.todomeet.todomeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {

    public final UserService userService;

    @PostMapping("/login")
    public JwtAuthDto login(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        JwtAuthDto jwtAuthDto = userService.login(userEmail);
        return jwtAuthDto;
    }


    @PostMapping("/sign-up")
    public JwtAuthDto signUp(@RequestBody UserDto userDto) throws Exception {
        JwtAuthDto jwtAuthDto = userService.register(userDto);
        return jwtAuthDto;
    }
}
