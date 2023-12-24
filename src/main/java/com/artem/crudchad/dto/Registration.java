package com.artem.crudchad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Registration {
    private String username;
    private String password;

    public String toString() {
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}