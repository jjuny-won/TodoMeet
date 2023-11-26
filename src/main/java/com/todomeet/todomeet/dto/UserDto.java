package com.todomeet.todomeet.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private String userName;
    private String profileImage;
    private String refreshToken;
    private String userEmail;

    public UserDto(String userEmail, String userName, String profileImage){
        this.userEmail  = userEmail;
        this.userName = userName;
        this.profileImage = profileImage;
    }
}
