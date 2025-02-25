package ec.turismvisitplanner.core.controllers.security;

import ec.turismvisitplanner.core.mapper.UserMapper;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.LoginRequest;
import ec.turismvisitplanner.core.payload.request.SignupRequest;
import ec.turismvisitplanner.core.payload.response.LoginResponse;
import ec.turismvisitplanner.core.security.services.AuthenticationService;
import ec.turismvisitplanner.core.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserMapper userMapper;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody SignupRequest signUpRequest) {
        User registeredUser = authenticationService.signup(signUpRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        LoginResponse loginResponse = userMapper.toLoginResponse(authenticatedUser);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setToken(jwtService.generateToken(authenticatedUser));
        return ResponseEntity.ok(loginResponse);
    }

}
