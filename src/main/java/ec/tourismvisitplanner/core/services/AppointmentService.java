package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.Appointment;
import ec.tourismvisitplanner.core.models.Route;
import ec.tourismvisitplanner.core.models.User;
import ec.tourismvisitplanner.core.models.enums.AppointmentStatus;
import ec.tourismvisitplanner.core.payload.request.AppointmentRequest;
import ec.tourismvisitplanner.core.repository.AppointmentRepository;
import ec.tourismvisitplanner.core.repository.RouteRepository;
import ec.tourismvisitplanner.core.repository.UserRepository;
import ec.tourismvisitplanner.core.utils.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public List<Appointment> getTouristAppointments() {
        User user = SessionUtils.getUserOnSession();
        return appointmentRepository.findByTouristId(user.getId());
    }

    public List<Appointment> getTouristGuideAppointments() {
        User user = SessionUtils.getUserOnSession();
        return appointmentRepository.findByTouristGuideId(user.getId());
    }

    public List<Appointment> getCompanyAppointments() {
        User user = SessionUtils.getUserOnSession();
        return appointmentRepository.findByRouteOrganizationId(user.getOrganization().getId());
    }

    public Appointment createAppointment(AppointmentRequest appointmentRequest) {

        Appointment appointment =
                Appointment.builder().
                        tourist(findUser(appointmentRequest.getIdTourist())).
                        route(findRoute(appointmentRequest.getIdRoute())).
                        eventDate(appointmentRequest.getEventDate()).
                        eventCreated(new Date()).
                        groupSize(appointmentRequest.getGroupSize()).status(AppointmentStatus.SCHEDULED).build();

        return appointmentRepository.save(appointment);
    }

    public Appointment assignGuideAppointment(String idAppointment, String idTouristGuide) {


        Optional<Appointment> appointment = appointmentRepository.findById(idAppointment);

        if (appointment.isPresent()) {
            Appointment _appointment = appointment.get();
            if (_appointment.getTouristGuide() == null) {
                _appointment.setTouristGuide(findUser(idTouristGuide));
            }
            return appointmentRepository.save(_appointment);
        }
        return null;
    }

    private User findUser(String idUser) {
        if (idUser != null) {
            Optional<User> user = userRepository.findById(idUser);
            if (user.isPresent()) {
                return user.get();
            }
        }
        return null;
    }

    private Route findRoute(String idRoute) {
        if (idRoute != null) {
            Optional<Route> route = routeRepository.findById(idRoute);
            if (route.isPresent()) {
                return route.get();
            }
        }
        return null;
    }
}
