package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository  extends MongoRepository<Route, String> {
}
