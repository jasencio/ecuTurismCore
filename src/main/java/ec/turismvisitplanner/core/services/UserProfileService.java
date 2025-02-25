package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.mapper.UserMapper;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.payload.request.UpdateUserRequest;
import ec.turismvisitplanner.core.repository.UserRepository;
import ec.turismvisitplanner.core.security.services.AuthenticationService;
import ec.turismvisitplanner.core.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;


    public UserProfileService(UserRepository userRepository , MessageSource messageSource, UserMapper userMapper, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
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

    public ResponseEntity<?> updateUserProfile(UpdateUserRequest updateUserRequest) {
        Locale locale = LocaleContextHolder.getLocale();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        User user = userRepository.findByEmail(userTmp.getEmail())
                .orElseThrow(() ->  { String userNotFoundMessage = messageSource.getMessage("user.profile.notFound",null,"Perfil no encontrado", locale);
                    return new CustomException(userNotFoundMessage, 404);});

        if(StringUtils.isNoneEmpty(updateUserRequest.getName()) ){
            user.setName(updateUserRequest.getName());
        }
        if(StringUtils.isNoneEmpty(updateUserRequest.getPhone())){
            user.setPhone(updateUserRequest.getPhone());
        }
        if(StringUtils.isNoneEmpty(updateUserRequest.getEmail())){
            user.setEmail(updateUserRequest.getEmail());
        }
        if(StringUtils.isNoneEmpty(updateUserRequest.getPassword())){
            user.setPassword(updateUserRequest.getPassword());
        }
        userRepository.save(user);
        return ResponseUtil.success(userMapper.toUserDto(user));
    }

    public ResponseEntity<?> deleteUserProfile(String authHeader) {
        Locale locale = LocaleContextHolder.getLocale();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        User user = userRepository.findByEmail(userTmp.getEmail())
                .orElseThrow(() ->  { String userNotFoundMessage = messageSource.getMessage("user.profile.notFound",null,"Perfil no encontrado", locale);
                    return new CustomException(userNotFoundMessage, 404);});
        user.setDeletedAt(new Date());
        userRepository.save(user);
        authenticationService.doLogout(authHeader);
        String successMessage = messageSource.getMessage("user.profile.success.delete", null,"Usuario eliminado con éxito",locale);
        return ResponseUtil.success(successMessage);
    }
}
