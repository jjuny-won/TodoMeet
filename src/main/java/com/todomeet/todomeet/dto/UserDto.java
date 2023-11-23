package com.todomeet.todomeet.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String userName;
    private String profileImage;
    private String refreshToken;

    private String userEmail;

    public UserDto(String userName,String userEmail,String profileImage){
        this.userName = userEmail;
        this.userEmail = userEmail;
        this.profileImage = profileImage;
    }
}
