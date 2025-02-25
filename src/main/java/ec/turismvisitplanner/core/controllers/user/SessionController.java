package ec.turismvisitplanner.core.controllers.user;

import ec.turismvisitplanner.core.security.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/session")
public class SessionController {

    private final AuthenticationService authenticationService;

    private SessionController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> closeSession(@RequestHeader("Authorization") String authHeader) {
        return authenticationService.logout(authHeader);
    }
}
