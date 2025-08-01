package ec.tourismvisitplanner.core.security.services;

import ec.tourismvisitplanner.core.exceptions.CustomException;
import ec.tourismvisitplanner.core.mappers.UserMapper;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.ERole;
import ec.tourismvisitplanner.core.payload.request.LoginRequest;
import ec.tourismvisitplanner.core.payload.request.SignupRequest;
import ec.tourismvisitplanner.core.payload.response.LoginResponse;
import ec.tourismvisitplanner.core.repository.RoleRepository;
import ec.tourismvisitplanner.core.repository.UserRepository;
import ec.tourismvisitplanner.core.services.BlacklistTokenService;
import ec.tourismvisitplanner.core.utils.I18n;
import ec.tourismvisitplanner.core.utils.ResponseUtil;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BlacklistTokenService blacklistTokenService;
    private final MessageSource messageSource;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final I18n i18n;


    public User signup(SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            String message = i18n.getMessage("user.email.isTaken","El correo electrónico ya se encuentra en uso");
            throw new CustomException(message, HttpStatus.BAD_REQUEST.value());
        }

        // Create new user's account
        User user = User.
                builder().
                name(signUpRequest.getName()).
                email(signUpRequest.getEmail()).
                phone(signUpRequest.getPhone()).
                password(passwordEncoder.encode(signUpRequest.getPassword())).build();

        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.TOURIST);

        user.setRoles(roles);
        return userRepository.save(user);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User userLoggedIn = userRepository.findByEmailAndDeletedAtIsNull(loginRequest.getEmail()).
                orElseThrow(() -> {
                            Locale locale = LocaleContextHolder.getLocale();
                            String invalidTokenMessage = messageSource.getMessage("user.credentials.invalid.", null, "Credenciales inválidas", locale);
                            return new SignatureException(invalidTokenMessage);
                        }
                );
        LoginResponse loginResponse = userMapper.toLoginResponse(userLoggedIn);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setToken(jwtService.getToken(userLoggedIn));
        return ResponseUtil.success(loginResponse);
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
