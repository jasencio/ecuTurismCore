package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.payload.request.OrganizationRequest;
import ec.turismvisitplanner.core.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization createOrganization(OrganizationRequest organizationRequest) {

        Organization organization =
                Organization.builder().
                        name(organizationRequest.getName()).build();

        return organizationRepository.save(organization);
    }

    public Organization updateOrganization(String id, OrganizationRequest organizationRequest){
        Optional<Organization> location = organizationRepository.findById(id);

        if (location.isPresent()) {
            Organization _organization = location.get();
            _organization.setName(organizationRequest.getName()!= null?organizationRequest.getName():_organization.getName());
            return organizationRepository.save(_organization);
        } else {
            return null;
        }
    }
}
