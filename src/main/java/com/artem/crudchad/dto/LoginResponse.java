package com.artem.crudchad.dto;

import com.artem.crudchad.dao.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponse {
    private User user;
    private String jwt;
}