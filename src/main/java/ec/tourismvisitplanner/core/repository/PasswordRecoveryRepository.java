package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.PasswordRecovery;
import ec.tourismvisitplanner.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordRecoveryRepository extends MongoRepository<PasswordRecovery, String> {
    Optional<PasswordRecovery> findByUser(User user);
    Optional<PasswordRecovery> findByUserAndRecoveryCode(User user, String recoveryCode);
}