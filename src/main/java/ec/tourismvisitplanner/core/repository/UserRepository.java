package ec.tourismvisitplanner.core.repository;


import ec.tourismvisitplanner.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  Optional<User> findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
