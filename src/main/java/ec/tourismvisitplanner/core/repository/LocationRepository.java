package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {
    List<Location> findByOrganizationIdAndDeletedAtIsNull(String organizationId);
}
