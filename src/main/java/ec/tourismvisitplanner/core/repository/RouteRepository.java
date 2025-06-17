package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findByOrganization(Organization organization);
    Route findByIdAndOrganization(String id,Organization organization);
    List<Route> findByOrganizationAndIsActiveTrue(Organization organization);
    Optional<Route> findByIdAndIsActiveTrue(String routeId);
}
