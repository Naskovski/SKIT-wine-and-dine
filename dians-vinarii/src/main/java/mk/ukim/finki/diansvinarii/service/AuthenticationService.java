package mk.ukim.finki.diansvinarii.service;

import mk.ukim.finki.diansvinarii.dto.JwtAuthenticationDto;
import mk.ukim.finki.diansvinarii.dto.RefreshTokenRequest;
import mk.ukim.finki.diansvinarii.dto.SignInRequest;
import mk.ukim.finki.diansvinarii.dto.SignUpRequest;
import mk.ukim.finki.diansvinarii.model.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationDto login(SignInRequest sign);
    JwtAuthenticationDto refreshToken(RefreshTokenRequest refreshTokenRequest);
}
