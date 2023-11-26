package com.todomeet.todomeet.Security;

import com.todomeet.todomeet.entity.UserEntity;
import com.todomeet.todomeet.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String useEmail) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(useEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
        return  user;
    }
}
