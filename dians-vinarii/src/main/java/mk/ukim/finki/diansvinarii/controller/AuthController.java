package mk.ukim.finki.diansvinarii.controller;
import lombok.RequiredArgsConstructor;

import mk.ukim.finki.diansvinarii.dto.JwtAuthenticationDto;
import mk.ukim.finki.diansvinarii.dto.RefreshTokenRequest;
import mk.ukim.finki.diansvinarii.dto.SignInRequest;
import mk.ukim.finki.diansvinarii.dto.SignUpRequest;
import mk.ukim.finki.diansvinarii.model.User;
import mk.ukim.finki.diansvinarii.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.login(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationDto> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
