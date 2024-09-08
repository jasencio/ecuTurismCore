package ec.turismvisitplanner.core.repository;

import ec.turismvisitplanner.core.models.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
}
