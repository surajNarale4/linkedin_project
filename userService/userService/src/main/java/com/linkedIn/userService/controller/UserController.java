package com.linkedIn.userService.controller;

import com.linkedIn.userService.dto.LoginRequestDTO;
import com.linkedIn.userService.dto.SignupRequestDTO;
import com.linkedIn.userService.dto.UserDTO;
import com.linkedIn.userService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//user/auth/login
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class UserController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO){
        UserDTO userDTO = authService.signUp(signupRequestDTO);
        return new ResponseEntity<>(userDTO, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/login")
        public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        String token = authService.login(loginRequestDTO);
        return new ResponseEntity<>(token,HttpStatusCode.valueOf(200));
        }

}
