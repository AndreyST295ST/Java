package com.artem.crudchad.controllers;


import com.artem.crudchad.dao.User;
import com.artem.crudchad.dto.LoginResponse;
import com.artem.crudchad.dto.Registration;
import com.artem.crudchad.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthController {


    private AuthService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody Registration body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody Registration body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
