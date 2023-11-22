package com.todomeet.todomeet.etc;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Role {
    MEMBER("ROLE_MEMBER"),
    GUEST("ROLE_GUEST"),
    MANAGER("ROLE_MANAGER");


    private String value;

    @JsonCreator
    public static Role from(String s) {
        return Role.valueOf(s.toUpperCase());
    }

}
