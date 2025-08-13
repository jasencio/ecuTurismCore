package ec.tourismvisitplanner.core.services.adminCompany;

import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.ERole;
import ec.tourismvisitplanner.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuidesService {

    private UserRepository userRepository;

    public List<User> getGuides() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        return userRepository.findByOrganizationIdAndRolesIn(userTmp.getOrganization().getId(), List.of(ERole.TOURIST_GUIDE));
    }

    public User getGuide(String idGuide) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        return userRepository.findByIdAndOrganizationIdAndRolesIn(idGuide,userTmp.getOrganization().getId(), List.of(ERole.TOURIST_GUIDE));
    }
}
