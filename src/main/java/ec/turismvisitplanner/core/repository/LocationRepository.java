package ec.turismvisitplanner.core.repository;

import ec.turismvisitplanner.core.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {
}
