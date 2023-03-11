package com.example.authjwt.controller.auth;

import com.example.authjwt.service.auth.ApplicationUserDetailsService;
import com.example.authjwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ApplicationUserDetailsService userDetailService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        final UserDetails userDetails = userDetailService.loadUserByUsername(request.username());
        final String jwt = jwtUtils.generateToken(userDetails);
        Map<String, String> resJwt = new HashMap<>();
        resJwt.put("token", jwt);
        return resJwt;
    }

}

record LoginRequest(String username, String password) {}
