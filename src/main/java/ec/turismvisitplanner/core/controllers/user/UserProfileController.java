package ec.turismvisitplanner.core.controllers.user;

import ec.turismvisitplanner.core.payload.request.UpdateUserRequest;
import ec.turismvisitplanner.core.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PutMapping("/me")
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserRequest updateUserRequest) {
        return userProfileService.updateUserProfile(updateUserRequest);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUserProfile(@RequestHeader("Authorization") String authHeader) {
        return userProfileService.deleteUserProfile(authHeader);
    }
}