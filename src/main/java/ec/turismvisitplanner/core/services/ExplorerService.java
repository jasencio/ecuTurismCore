package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExplorerService {

    private OrganizationRepository organizationRepository;

    public List<Organization> getOrganizations() {
        return organizationRepository.findByIsActiveTrue();
    }
}
