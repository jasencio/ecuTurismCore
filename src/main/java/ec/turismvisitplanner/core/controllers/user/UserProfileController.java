package ec.turismvisitplanner.core.controllers.user;

import ec.turismvisitplanner.core.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService =userProfileService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile() {
        return userProfileService.getUserProfile();
    }
}