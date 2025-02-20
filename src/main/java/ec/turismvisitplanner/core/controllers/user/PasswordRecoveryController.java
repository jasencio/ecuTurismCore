package ec.turismvisitplanner.core.controllers.user;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.models.PasswordRecovery;
import ec.turismvisitplanner.core.payload.request.PasswordRecoverRequest;
import ec.turismvisitplanner.core.payload.request.PasswordVerifyRequest;
import ec.turismvisitplanner.core.services.EmailService;
import ec.turismvisitplanner.core.services.PasswordRecoveryService;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/recover")
public class PasswordRecoveryController {

    private final PasswordRecoveryService recoveryService;
    private final EmailService emailService;

    public PasswordRecoveryController(PasswordRecoveryService recoveryService, EmailService emailService) {
        this.recoveryService = recoveryService;
        this.emailService = emailService;
    }

    @PostMapping("/recover-password")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody PasswordRecoverRequest request) throws MessagingException {
        PasswordRecovery recovery = recoveryService.createRecoveryCode(request.getEmail());
        emailService.sendRecoveryEmail(recovery.getUser(), recovery.getRecoveryCode());
        return ResponseUtil.success("Mensaje enviado con exito", null);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@Valid  @RequestBody PasswordVerifyRequest request) throws CustomException {
         return recoveryService.verifyRecoveryCode(request);
    }

}
