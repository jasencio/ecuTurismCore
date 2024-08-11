package ec.turismvisitplanner.core.controllers.security;


import ec.turismvisitplanner.core.models.enums.ERole;
import ec.turismvisitplanner.core.models.Role;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.LoginRequest;
import ec.turismvisitplanner.core.payload.request.SignupRequest;
import ec.turismvisitplanner.core.payload.response.JwtResponse;
import ec.turismvisitplanner.core.payload.response.MessageResponse;
import ec.turismvisitplanner.core.repository.RoleRepository;
import ec.turismvisitplanner.core.repository.UserRepository;
import ec.turismvisitplanner.core.security.jwt.JwtUtils;
import ec.turismvisitplanner.core.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = User.
                builder().
                username(signUpRequest.getUsername()).
                email(signUpRequest.getEmail()).
                password(encoder.encode(signUpRequest.getPassword())).build();

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_TOURIST)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN_SYSTEM)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "admin-company":
                        Role adminCompanyRole = roleRepository.findByName(ERole.ROLE_ADMIN_COMPANY)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminCompanyRole);

                        break;
                    case "guide":
                        Role guideRole = roleRepository.findByName(ERole.ROLE_TOURIST_GUIDE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(guideRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_TOURIST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
