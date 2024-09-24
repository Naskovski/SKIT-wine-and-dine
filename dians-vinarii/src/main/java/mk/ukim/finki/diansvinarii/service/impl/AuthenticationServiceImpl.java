package mk.ukim.finki.diansvinarii.service.impl;

import lombok.RequiredArgsConstructor;

import mk.ukim.finki.diansvinarii.dto.JwtAuthenticationDto;
import mk.ukim.finki.diansvinarii.dto.RefreshTokenRequest;
import mk.ukim.finki.diansvinarii.dto.SignInRequest;
import mk.ukim.finki.diansvinarii.dto.SignUpRequest;
import mk.ukim.finki.diansvinarii.model.Role;
import mk.ukim.finki.diansvinarii.model.User;
import mk.ukim.finki.diansvinarii.repository.UserRepo;
import mk.ukim.finki.diansvinarii.service.AuthenticationService;
import mk.ukim.finki.diansvinarii.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        System.out.println("Sign up req:" + signUpRequest);
        user.setRoles(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);

    }

    public JwtAuthenticationDto login(SignInRequest sign) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sign.getEmail(), sign.getPassword()));
        var user = userRepository.findByEmail(sign.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(jwt);
        jwtAuthenticationDto.setRefreshToken(jwt);
        return jwtAuthenticationDto;

    }

    public JwtAuthenticationDto refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
            jwtAuthenticationDto.setToken(jwt);
            jwtAuthenticationDto.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationDto;
        }
        return null;
    }
}
