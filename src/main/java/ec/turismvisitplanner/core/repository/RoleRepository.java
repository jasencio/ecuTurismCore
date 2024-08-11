package ec.turismvisitplanner.core.repository;


import ec.turismvisitplanner.core.models.enums.ERole;
import ec.turismvisitplanner.core.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
