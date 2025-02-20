package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.mapper.UserMapper;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.repository.UserRepository;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final UserMapper userMapper;


    public UserProfileService(UserRepository userRepository , MessageSource messageSource, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
    }

    public ResponseEntity<?> getUserProfile() {
        Locale locale = LocaleContextHolder.getLocale();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        User user = userRepository.findByEmail(userTmp.getEmail())
                .orElseThrow(() ->  { String userNotFoundMessage = messageSource.getMessage("user.profile.notFound",null,"Perfil no encontrado", locale);
                    return new CustomException(userNotFoundMessage, 404);});
        return ResponseUtil.success(userMapper.toUserDto(user));
    }
}
