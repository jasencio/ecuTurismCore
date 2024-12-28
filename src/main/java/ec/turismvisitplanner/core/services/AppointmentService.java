package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.models.Appointment;
import ec.turismvisitplanner.core.models.Route;
import ec.turismvisitplanner.core.models.User;
import ec.turismvisitplanner.core.models.enums.AppointmentStatus;
import ec.turismvisitplanner.core.payload.request.AppointmentRequest;
import ec.turismvisitplanner.core.repository.AppointmentRepository;
import ec.turismvisitplanner.core.repository.RouteRepository;
import ec.turismvisitplanner.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RouteRepository routeRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
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
