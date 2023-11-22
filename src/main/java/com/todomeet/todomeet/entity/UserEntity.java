package com.todomeet.todomeet.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private String refreshToken;


    private Role role;



}
