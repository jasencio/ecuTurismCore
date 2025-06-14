package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.models.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findByOrganization(Organization organization);
    Route findByIdAndOrganization(String id,Organization organization);
}
