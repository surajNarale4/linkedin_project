package com.linkedIn.userService.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.linkedIn.userService.dto.LoginRequestDTO;
import com.linkedIn.userService.dto.SignupRequestDTO;
import com.linkedIn.userService.dto.UserDTO;
import com.linkedIn.userService.entity.User;
import com.linkedIn.userService.exception.BadRequestException;
import com.linkedIn.userService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDTO signUp(SignupRequestDTO signupRequestDTO){
        log.info("Signup a user with email {}", signupRequestDTO.getEmail());
        boolean exists = userRepository.existsByEmail(signupRequestDTO.getEmail());
        if(exists) throw new BadRequestException("User Already exists");

        User user = modelMapper.map(signupRequestDTO,User.class);

        user.setPassword(BCrypt.withDefaults().hashToString(12,signupRequestDTO.getPassword().toCharArray()));
        userRepository.save(user);
        return modelMapper.map(user,UserDTO.class);
    }

    public String login(LoginRequestDTO loginRequestDTO){
        log.info("Login request for user with email: {}", loginRequestDTO.getEmail());
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new BadRequestException("incorrect email"));

        boolean isPassordCorrect = BCrypt.verifyer().verify(loginRequestDTO.getPassword().toCharArray(),user.getPassword().toCharArray()).verified;


        if(!isPassordCorrect) throw new BadRequestException("Incorrect email or password");

        return jwtService.generateAccessToken(user);

    }
}
