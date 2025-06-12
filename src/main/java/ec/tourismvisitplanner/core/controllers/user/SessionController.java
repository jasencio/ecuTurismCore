package ec.tourismvisitplanner.core.controllers.user;

import ec.tourismvisitplanner.core.security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/session")
public class SessionController {

    private final AuthService authenticationService;

    private SessionController(AuthService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> closeSession(@RequestHeader("Authorization") String authHeader) {
        return authenticationService.logout(authHeader);
    }
}
