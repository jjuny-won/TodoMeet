package com.todomeet.todomeet.entity;

import com.todomeet.todomeet.etc.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name="user")
public class UserEntity implements UserDetails {

    @Id
    private String userEmail;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private String refreshToken;


    public UserEntity(String userEmail, String userName, String profileImage, String refreshToken) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.profileImage = profileImage;
        this.refreshToken = refreshToken;
        this.roles = Collections.singletonList(Role.ROLE_USER.name());
    }
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
