package ec.tourismvisitplanner.core.utils;

import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.ERole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtils {

    public static User getUserOnSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User)authentication.getPrincipal();
    }

    public static boolean isAdmin(User user){
        return user.getRoles().contains(ERole.ADMIN_SYSTEM);
    }

    public static boolean isTouristGuide(User user){
        return user.getRoles().contains(ERole.TOURIST_GUIDE);
    }
}
