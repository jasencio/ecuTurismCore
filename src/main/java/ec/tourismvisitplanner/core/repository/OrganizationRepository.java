package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization> findByIsActiveTrue();
    Optional<Organization> findByIdAndIsActiveTrue(String id);
}
