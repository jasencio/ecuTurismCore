package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.repository.OrganizationRepository;
import ec.tourismvisitplanner.core.repository.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExplorerService {

    private OrganizationRepository organizationRepository;
    private RouteRepository routeRepository;

    public List<Organization> getOrganizations() {
        return organizationRepository.findByIsActiveTrue();
    }

    public Organization getOrganization(String idOrganization) {
        Optional<Organization> organization = organizationRepository.findByIdAndIsActiveTrue(idOrganization);
        return organization.orElse(null);
    }

    public List<Route> getRoutes(String idOrganization) {
        if (idOrganization == null) {
            return new ArrayList<>();
        }

        Optional<Organization> organization = organizationRepository.findByIdAndIsActiveTrue(idOrganization);
        if (organization.isEmpty()) {
            return new ArrayList<>();
        }
        return routeRepository.findByOrganizationAndIsActiveTrue(organization.get());
    }

    public Route getRoute(String idRoute) {
        if (idRoute == null) {
            return null;
        }
        Optional<Route> route = routeRepository.findByIdAndIsActiveTrue(idRoute);
        return route.orElse(null);
    }
}
