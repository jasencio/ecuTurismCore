package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MessageSource messageSource;

    public void sendRecoveryEmail(User user, String code) throws MessagingException {
        Locale locale = LocaleContextHolder.getLocale(); // Automatically detects language from headers

        String subject = messageSource.getMessage("email.subject", null, locale);
        String greeting = messageSource.getMessage("email.greeting", new Object[]{user.getFullName()}, locale);
        String body = messageSource.getMessage("email.body", null, locale);
        String recoveryCode = messageSource.getMessage("email.recoveryCode", new Object[]{code}, locale);
        String expiry = messageSource.getMessage("email.expiry", null, locale);
        String ignore = messageSource.getMessage("email.ignore", null, locale);
        String signature = messageSource.getMessage("email.signature", null, locale);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariable("greeting", greeting);
        context.setVariable("body", body);
        context.setVariable("recoveryCode", recoveryCode);
        context.setVariable("expiry", expiry);
        context.setVariable("ignore", ignore);
        context.setVariable("signature", signature);
        String htmlContent = templateEngine.process("recover-password", context);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
