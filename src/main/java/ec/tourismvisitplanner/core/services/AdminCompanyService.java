package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.payload.request.OrganizationRequest;
import ec.tourismvisitplanner.core.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminCompanyService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationService organizationService;

    public Organization getOrganization(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        Optional<Organization> organization =  organizationRepository.findById( userTmp.getOrganization().getId());
        return organization.orElse(null);
    }

    public Organization updateOrganization(OrganizationRequest organizationRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userTmp = (User)authentication.getPrincipal();
        if(userTmp.getOrganization()!= null){
            return organizationService.updateOrganization(userTmp.getOrganization().getId(), organizationRequest);
        }
        return null;
    }
}
