package ec.turismvisitplanner.core.security.controllers;

import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.LoginRequest;
import ec.turismvisitplanner.core.payload.request.SignupRequest;
import ec.turismvisitplanner.core.security.services.AuthService;
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

    private AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody SignupRequest signUpRequest) {
        User registeredUser = authenticationService.signup(signUpRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

}
