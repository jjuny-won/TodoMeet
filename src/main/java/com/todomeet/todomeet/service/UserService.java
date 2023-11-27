package com.todomeet.todomeet.service;

import com.todomeet.todomeet.Security.JwtTokenProvider;
import com.todomeet.todomeet.dto.JwtAuthDto;
import com.todomeet.todomeet.dto.UserDto;
import com.todomeet.todomeet.entity.UserEntity;
import com.todomeet.todomeet.exception.exception.BaseException;
import com.todomeet.todomeet.exception.exception.MemberErrorCode;
import com.todomeet.todomeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;



    //회원가입
    @Transactional
    public JwtAuthDto register(UserDto userDto) throws Exception {


        if (userRepository.findById(userDto.getUserEmail()).isPresent()) {
            BaseException exception = new BaseException(MemberErrorCode.DUPLICATE_MEMBER);
            throw exception;
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDto.getUserEmail(), userDto)
        );
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUserEmail(), userDto);
        JwtAuthDto jwtAuthDto = jwtTokenProvider.generateTokens(authenticationToken);

        UserEntity user = new UserEntity(userDto.getUserEmail(), userDto.getProfileImage(),
                userDto.getUserName(), jwtAuthDto.getRefreshToken() );
        userRepository.save(user);

        return  jwtAuthDto;
    }


    @Transactional
    public JwtAuthDto login(String userEmail) {
        // 제공된 이메일을 기반으로 사용자를 찾습니다.

        //로그인을한 이메일이 DB에 존재하지 않을경우
        if (userRepository.findById(userEmail).isEmpty()) {
            BaseException exception = new BaseException(MemberErrorCode.EMPTY_MEMBER);
            throw exception;
        }

        Optional<UserEntity> user = userRepository.findById(userEmail);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.get().getUserEmail(), user.get(), null)
        );
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEmail, user);
        // JWT 토큰을 생성하고 반환합니다.
        return jwtTokenProvider.generateTokens(authenticationToken);


    }
        @Transactional
    public void delete(String userEmail) {
        //DB에 user가 존재하지 않을 경우 //refreshToken 도 같이 확인해야하지 않을까?
                Optional<UserEntity> userEntity = userRepository.findById(userEmail);
            if (userEntity.isEmpty()) {
                BaseException exception = new BaseException(MemberErrorCode.EMPTY_MEMBER);
                throw exception;
            }
            userRepository.deleteById(userEmail);
        }
}



