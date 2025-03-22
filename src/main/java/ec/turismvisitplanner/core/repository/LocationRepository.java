package ec.turismvisitplanner.core.repository;

import ec.turismvisitplanner.core.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {
    List<Location> findByOrganizationIdAndDeletedAtIsNull(String organizationId);
}
