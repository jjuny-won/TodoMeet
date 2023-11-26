package com.todomeet.todomeet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public JwtAuthDto(String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
