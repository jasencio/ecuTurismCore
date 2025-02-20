package ec.turismvisitplanner.core.security.services;

import ec.turismvisitplanner.core.models.Role;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.models.enums.ERole;
import ec.turismvisitplanner.core.payload.request.LoginRequest;
import ec.turismvisitplanner.core.payload.request.SignupRequest;
import ec.turismvisitplanner.core.repository.RoleRepository;
import ec.turismvisitplanner.core.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(SignupRequest signUpRequest) {

/*        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }*/

        // Create new user's account
        User user = User.
                builder().
                name(signUpRequest.getName()).
                email(signUpRequest.getEmail()).
                phone(signUpRequest.getPhone()).
                password(passwordEncoder.encode(signUpRequest.getPassword())).build();


        Set<Role> roles = new HashSet<>();


        Role userRole = roleRepository.findByName(ERole.ROLE_TOURIST)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);


        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
    }
}
