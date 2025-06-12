package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization> findByIsActiveTrue();
}
