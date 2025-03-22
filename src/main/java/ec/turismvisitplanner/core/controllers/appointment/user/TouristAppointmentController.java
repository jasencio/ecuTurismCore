package ec.turismvisitplanner.core.controllers.appointment.user;

import ec.turismvisitplanner.core.models.Appointment;
import ec.turismvisitplanner.core.payload.request.AppointmentRequest;
import ec.turismvisitplanner.core.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tourist/appointments")
@RequiredArgsConstructor
public class TouristAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping()
    public List<Appointment> getAppointments() {
        return appointmentService.getTouristAppointments();
    }

    @PostMapping()
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        try {
            Appointment appointment = appointmentService.createAppointment(appointmentRequest);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
