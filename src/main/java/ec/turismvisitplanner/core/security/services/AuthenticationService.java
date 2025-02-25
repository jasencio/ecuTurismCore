package ec.turismvisitplanner.core.security.services;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.models.Role;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.models.enums.ERole;
import ec.turismvisitplanner.core.payload.request.LoginRequest;
import ec.turismvisitplanner.core.payload.request.SignupRequest;
import ec.turismvisitplanner.core.repository.RoleRepository;
import ec.turismvisitplanner.core.repository.UserRepository;
import ec.turismvisitplanner.core.services.BlacklistTokenService;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BlacklistTokenService blacklistTokenService;
    private final MessageSource messageSource;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            BlacklistTokenService blacklistTokenService,
            MessageSource messageSource,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.blacklistTokenService = blacklistTokenService;
        this.messageSource = messageSource;
        this.jwtService = jwtService;
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

        return userRepository.findByEmailAndDeletedAtIsNull(loginRequest.getEmail()).
                orElseThrow(() -> {
                            Locale locale = LocaleContextHolder.getLocale();
                            String invalidTokenMessage = messageSource.getMessage("user.credentials.invalid.", null, "Credenciales inválidas", locale);
                            return new SignatureException(invalidTokenMessage);
                        }
                );
    }

    public ResponseEntity<?> logout(String authHeader) {
        doLogout(authHeader);
        Locale locale = LocaleContextHolder.getLocale();
        String successMessage = messageSource.getMessage("user.logout.success.", null, "Sesión cerrada exitosamente.", locale);
        return ResponseUtil.success(successMessage);
    }

    public void doLogout(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            Locale locale = LocaleContextHolder.getLocale();
            String invalidTokenMessage = messageSource.getMessage("user.token.invalid.", null, "Token Invalido.", locale);
            throw new CustomException(invalidTokenMessage, HttpStatus.BAD_REQUEST.value());
        }

        int authIndexTokenBegin = 7;
        String token = authHeader.substring(authIndexTokenBegin);
        long expiration = jwtService.extractExpiration(token).getTime();
        blacklistTokenService.blacklistToken(token, expiration);
        SecurityContextHolder.clearContext();
    }

}
