package com.darfik.skycast.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String password;

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
