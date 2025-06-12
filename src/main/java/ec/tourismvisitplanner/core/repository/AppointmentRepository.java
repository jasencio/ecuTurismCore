package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByTouristId(String touristId);
    List<Appointment> findByTouristGuideId(String touristGuideId);
    List<Appointment> findByRouteLocationOrganizationId(String organizationId);
}
