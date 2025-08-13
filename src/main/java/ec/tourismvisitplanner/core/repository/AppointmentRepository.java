package ec.tourismvisitplanner.core.repository;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.models.enums.AppointmentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByTouristId(String touristId);
    List<Appointment> findByTouristGuideId(String touristGuideId);
    Optional<Appointment> findByIdAndTouristId(String id, String touristId);
    List<Appointment> findByRouteAndStatus(Route route, AppointmentStatus appointmentStatus);
}
