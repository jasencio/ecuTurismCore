package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.models.PasswordRecovery;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.PasswordVerifyRequest;
import ec.turismvisitplanner.core.repository.PasswordRecoveryRepository;
import ec.turismvisitplanner.core.repository.UserRepository;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordRecoveryService {
    private final PasswordRecoveryRepository repository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    public PasswordRecoveryService(PasswordRecoveryRepository repository, UserRepository userRepository, MessageSource messageSource,PasswordEncoder passwordEncoder ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateRecoveryCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(code);
    }

    public PasswordRecovery createRecoveryCode(String email) throws CustomException{

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            Locale locale = LocaleContextHolder.getLocale();
            String userNotFoundMessage = messageSource.getMessage("user.notFound", null, locale);
            throw new CustomException(userNotFoundMessage, 404);
        }

        User userToRecover = user.get();
        String code = generateRecoveryCode();
        Instant expirationTime = Instant.now().plus(10, ChronoUnit.MINUTES); // Expira en 10 min
        Optional<PasswordRecovery> existing = repository.findByUser(userToRecover);
        existing.ifPresent(repository::delete);

        PasswordRecovery recovery = PasswordRecovery.
                builder().
                recoveryCode(code).
                user(userToRecover).
                expirationTime(expirationTime).
                build();
        repository.save(recovery);

        return recovery;
    }

    public ResponseEntity<?> verifyRecoveryCode(PasswordVerifyRequest request) throws CustomException{
        Locale locale = LocaleContextHolder.getLocale(); // Automatically detects language from headers


        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            String userNotFoundMessage = messageSource.getMessage("user.notFound", null, locale);
            throw new CustomException(userNotFoundMessage, 404);
        }
        User userToRecover = user.get();
        Optional<PasswordRecovery> recoveryOpt = repository.findByUserAndRecoveryCode(userToRecover, request.getCode());

        if (recoveryOpt.isEmpty()){
            String userNotFoundMessage = messageSource.getMessage("user.recoveryCode.notFound",null,"Código no encontrado", locale);
            throw new CustomException(userNotFoundMessage, 404);
        }

        PasswordRecovery recovery = recoveryOpt.get();

        if (recovery.getExpirationTime().isBefore(Instant.now())) {
            repository.delete(recovery);
            String userCodeExpiredMessage = messageSource.getMessage("user.recoveryCode.expired",null,"Código expirado", locale);
            throw new CustomException(userCodeExpiredMessage, 404);
        }

         if(recovery.getRecoveryCode().equals(request.getCode())){
             userToRecover.setPassword(passwordEncoder.encode(request.getPassword()));
             userRepository.save(userToRecover);
             return ResponseUtil.success("Contraseña restablecida con éxito", null);
        }else{
             String userCodeNotMatch = messageSource.getMessage("user.recoveryCode.notMatch",null,"Código inválido", locale);
             throw new CustomException(userCodeNotMatch, 404);
         }
    }
}
