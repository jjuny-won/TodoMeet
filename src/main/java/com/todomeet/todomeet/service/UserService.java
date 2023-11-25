package com.todomeet.todomeet.service;



import com.todomeet.todomeet.Security.JwtTokenProvider;
import com.todomeet.todomeet.dto.JwtAuthDto;
import com.todomeet.todomeet.dto.UserDto;
import com.todomeet.todomeet.entity.UserEntity;
import com.todomeet.todomeet.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;



//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;

    //회원가입
    @Transactional
    public JwtAuthDto register(UserDto userDto) throws Exception {
        if (userRepository.findByuserEmail(userDto.getUserEmail()).isPresent()) {
            throw new Exception("이미 있는 이메일 입니다");
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
        UserEntity user = userRepository.findByuserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getUserEmail(), user, null)
        );
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEmail, user);
        // JWT 토큰을 생성하고 반환합니다.
        return jwtTokenProvider.generateTokens(authenticationToken);

//        // 이메일이 일치하면 사용자를 인증합니다.
//        if (user.getUserId().equals(userDto.getUserId())) {
//            // 인증된 사용자 정보를 설정합니다.
////             new UsernameAuthenticationToken(user, null, user.getAuthorities())를 사용할 수 있습니다.
//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(user.getUserEmail(), user, null)
//            );
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(userDto.getUserEmail(), userDto);
//            // JWT 토큰을 생성하고 반환합니다.
//            return jwtTokenProvider.generateTokens(authenticationToken);
//        } else {
//            throw new IllegalArgumentException("사용자 ID가 일치하지 않습니다.");
        }
    }

