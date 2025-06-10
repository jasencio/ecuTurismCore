package ec.turismvisitplanner.core.repository;

import ec.turismvisitplanner.core.models.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization> findByIsActiveTrue();
}
