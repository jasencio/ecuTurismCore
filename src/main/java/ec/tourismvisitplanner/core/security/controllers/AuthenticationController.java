package ec.tourismvisitplanner.core.security.controllers;

import ec.tourismvisitplanner.core.payload.request.LoginRequest;
import ec.tourismvisitplanner.core.payload.request.SignupRequest;
import ec.tourismvisitplanner.core.security.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.signup(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
