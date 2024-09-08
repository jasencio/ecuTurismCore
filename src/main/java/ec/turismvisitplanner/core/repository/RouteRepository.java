package ec.turismvisitplanner.core.repository;

import ec.turismvisitplanner.core.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository  extends MongoRepository<Route, String> {
}
