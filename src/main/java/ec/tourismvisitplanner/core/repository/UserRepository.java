package ec.tourismvisitplanner.core.repository;


import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  Optional<User> findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  List<User> findByOrganizationIdAndRolesIn(String organizationId, List<ERole> roles);

  User findByIdAndOrganizationIdAndRolesIn(String id, String organizationId, List<ERole> roles);
}
