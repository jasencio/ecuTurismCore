package ec.turismvisitplanner.core.repository;

import ec.turismvisitplanner.core.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
}
