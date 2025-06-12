package ec.tourismvisitplanner.core.repository;


import ec.tourismvisitplanner.core.models.enums.ERole;
import ec.tourismvisitplanner.core.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
